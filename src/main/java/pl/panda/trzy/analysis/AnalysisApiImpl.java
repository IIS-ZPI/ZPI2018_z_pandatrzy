package pl.panda.trzy.analysis;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import pl.panda.trzy.nbp.NbpResponse;
import pl.panda.trzy.nbp.PeriodType;
import pl.panda.trzy.nbp.Rate;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static pl.panda.trzy.application.PeriodDateUtil.getPastDate;

public class AnalysisApiImpl implements AnalysisApi {

    @Override
    public SessionsCount countSessions(LinkedList<Rate> rates) {
        int rise = 0;
        int fall = 0;
        int noChange = 0;
        ListIterator<Rate> it = rates.listIterator();
        while (it.hasNext()) {
            Rate currentRate = it.next();
            if (it.hasNext()) {
                Rate nexRate = rates.get(it.nextIndex());
                int compared = currentRate.getMid().compareTo(nexRate.getMid());
                if (compared < 0) {
                    rise++;
                } else if (compared > 0) {
                    fall++;
                } else {
                    noChange++;
                }
            }
        }
        return new SessionsCount(rise, fall, noChange);
    }

    @Override
    public Map<PeriodType, SessionsCount> performSessionAnalysis(LinkedList<Rate> rateList) {
        Map<PeriodType, SessionsCount> sessions = new HashMap<>();
        Map<PeriodType, LinkedList<Rate>> periodicRates = mapSessions(rateList);
        periodicRates.forEach((period, rates) -> sessions.put(period, countSessions(rates)));
        return sessions;
    }

    private Map<PeriodType, LinkedList<Rate>> mapSessions(LinkedList<Rate> rateList) {
        LocalDateTime date = LocalDate.now().atStartOfDay();
        Map<PeriodType, LinkedList<Rate>> periodicRates = new HashMap<>();
        periodicRates.put(PeriodType.YEAR, rateList);
        periodicRates.put(PeriodType.HALF_OF_YEAR, filterRatesForPeriod(rateList, date, PeriodType.HALF_OF_YEAR));
        periodicRates.put(PeriodType.QUARTER, filterRatesForPeriod(rateList, date, PeriodType.QUARTER));
        periodicRates.put(PeriodType.MONTH, filterRatesForPeriod(rateList, date, PeriodType.MONTH));
        periodicRates.put(PeriodType.TWO_WEEKS, filterRatesForPeriod(rateList, date, PeriodType.TWO_WEEKS));
        periodicRates.put(PeriodType.WEEK, filterRatesForPeriod(rateList, date, PeriodType.WEEK));
        return periodicRates;
    }

    @Override
    public LinkedList<Rate> filterRatesForPeriod(LinkedList<Rate> rateList, LocalDateTime date, PeriodType periodType) {
        return rateList.stream().filter(rate ->
                rate.getEffectiveDate().compareTo(getPastDate(date, periodType)) >= 0
        ).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Map<PeriodType, StatAnalysis> performStatAnalysis(LinkedList<Rate> rates) {
        Map<PeriodType, StatAnalysis> stats = new HashMap<>();
        Map<PeriodType, LinkedList<Rate>> periodicRates = mapSessions(rates);
        periodicRates.forEach((period, rats) -> stats.put(period, countStats(rats)));
        return stats;
    }

    @Override
    public Map<Integer, Double> performDistributionAnalysis(Map<String, NbpResponse> responseMap) {
        Map<Integer, Double> result = new HashMap<>();
        if (responseMap.size() != 2) {
            throw new IllegalArgumentException("Expected two ccurrencies");
        }
        List<Double> rates = new ArrayList<>();
        int j = 0;
        Iterator<NbpResponse> it = responseMap.values().iterator();
        List<Rate> curr1 = it.next().getRates();
        List<Rate> curr2 = it.next().getRates();
        for (int i = 0; i < curr1.size(); i++) {
            Rate c1 = curr1.get(i);
            Rate c2 = curr2.get(i);
            rates.add(c1.getMid().divide(c2.getMid(), 3, RoundingMode.HALF_UP).doubleValue());
        }
        Collection<List<Double>> subSets = partition(rates, 7);
        for (List<Double> set : subSets) {
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
            set.forEach(descriptiveStatistics::addValue);
            double sd = descriptiveStatistics.getStandardDeviation();
            double mean = descriptiveStatistics.getMean();
            result.put(j++, (sd / mean) * 100);
        }

        return result;
    }

    private StatAnalysis countStats(LinkedList<Rate> rats) {
        double[] rates = rats.stream().mapToDouble(rat -> rat.getMid().doubleValue()).toArray();
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        rats.forEach(rat -> descriptiveStatistics.addValue(rat.getMid().doubleValue()));
        double[] modes = StatUtils.mode(rates);
        double sd = descriptiveStatistics.getStandardDeviation();
        double median = descriptiveStatistics.getPercentile(50);
        double coefficient = sd / descriptiveStatistics.getMean();
        return new StatAnalysis(median, modes, sd, coefficient);
    }

    private static <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return list.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size)).values();
    }
}
