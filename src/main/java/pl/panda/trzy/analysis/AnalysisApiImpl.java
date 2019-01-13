package pl.panda.trzy.analysis;

import pl.panda.trzy.nbp.PeriodType;
import pl.panda.trzy.nbp.Rate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    public Map<PeriodType, SessionsCount> mapSessions(LinkedList<Rate> rateList) {
        LocalDateTime date = LocalDate.now().atStartOfDay();
        Map<PeriodType, LinkedList<Rate>> periodicRates = new HashMap<>();
        Map<PeriodType, SessionsCount> sessions = new HashMap<>();
        periodicRates.put(PeriodType.YEAR, rateList);
        periodicRates.put(PeriodType.HALF_OF_YEAR, filterRatesForPeriod(rateList, date, PeriodType.HALF_OF_YEAR));
        periodicRates.put(PeriodType.QUARTER, filterRatesForPeriod(rateList, date, PeriodType.QUARTER));
        periodicRates.put(PeriodType.MONTH, filterRatesForPeriod(rateList, date, PeriodType.MONTH));
        periodicRates.put(PeriodType.TWO_WEEKS, filterRatesForPeriod(rateList, date, PeriodType.TWO_WEEKS));
        periodicRates.put(PeriodType.WEEK, filterRatesForPeriod(rateList, date, PeriodType.WEEK));
        periodicRates.forEach((period, rates) -> sessions.put(period, countSessions(rates)));
        return sessions;
    }

    @Override
    public LinkedList<Rate> filterRatesForPeriod(LinkedList<Rate> rateList, LocalDateTime date, PeriodType periodType) {
        return rateList.stream().filter(rate ->
            rate.getEffectiveDate().compareTo(getPastDate(date, periodType)) >= 0
        ).collect(Collectors.toCollection(LinkedList::new));
    }
}
