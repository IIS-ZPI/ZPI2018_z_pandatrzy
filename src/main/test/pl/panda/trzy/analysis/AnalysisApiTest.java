package pl.panda.trzy.analysis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.panda.trzy.nbp.PeriodType;
import pl.panda.trzy.nbp.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnalysisApiTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private AnalysisApi analysisApi;
    private LinkedList<Rate> ratesRise;
    private LinkedList<Rate> ratesFall;
    private LinkedList<Rate> ratesNoChange;

    @BeforeEach
    void setUp() {
        analysisApi = new AnalysisApiImpl();
        ratesRise = mockRates(RateType.RISE);
        ratesFall = mockRates(RateType.FALL);
        ratesNoChange = mockRates(RateType.NO_CHANGE);
    }

    private LinkedList<Rate> mockRates(RateType type) {
        LinkedList<Rate> rates = new LinkedList<>();
        switch (type) {
            case RISE: {
                rates.add(new Rate(new BigDecimal(1)));
                rates.add(new Rate(new BigDecimal(2)));
                rates.add(new Rate(new BigDecimal(3)));
                rates.add(new Rate(new BigDecimal(4)));
                break;
            }
            case FALL: {
                rates.add(new Rate(new BigDecimal(4)));
                rates.add(new Rate(new BigDecimal(3)));
                rates.add(new Rate(new BigDecimal(1)));
                rates.add(new Rate(new BigDecimal(2)));
                break;
            }
            case NO_CHANGE: {
                rates.add(new Rate(new BigDecimal(3)));
                rates.add(new Rate(new BigDecimal(3)));
                break;
            }
            default:
        }

        return rates;
    }

    @Test
    public void testCountSessionsRise() {
        SessionsCount sessionsCount = analysisApi.countSessions(ratesRise);
        assertEquals(0, sessionsCount.getFall());
        assertEquals(3, sessionsCount.getRise());
        assertEquals(0, sessionsCount.getNoChange());
    }

    @Test
    public void testCountSessionsFall() {
        SessionsCount sessionsCount = analysisApi.countSessions(ratesFall);
        assertEquals(2, sessionsCount.getFall());
        assertEquals(1, sessionsCount.getRise());
        assertEquals(0, sessionsCount.getNoChange());
    }

    @Test
    public void testCountSessionsNoChange() {
        SessionsCount sessionsCount = analysisApi.countSessions(ratesNoChange);
        assertEquals(0, sessionsCount.getFall());
        assertEquals(0, sessionsCount.getRise());
        assertEquals(1, sessionsCount.getNoChange());
    }

    @Test
    public void testPerformAnalysis() {
        LinkedList<Rate> rateList = mockFullRates();
        Map<PeriodType, SessionsCount> result = analysisApi.performSessionAnalysis(rateList);

        assertEquals(6, result.size());
        result.values().forEach(Assertions::assertNotNull);
        assertThat(result.keySet(), hasItems(PeriodType.values()));
    }

    private LinkedList<Rate> mockFullRates() {
        LinkedList<Rate> rateList = new LinkedList<>();
        LocalDate xDate = LocalDate.parse("2017-12-03", DATE_FORMATTER);
        LocalDate yDate = LocalDate.parse("2016-12-03", DATE_FORMATTER);
        while (xDate.isAfter(yDate)) {
            rateList.add(new Rate(Date.from(yDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                    new BigDecimal(1)));
            yDate = yDate.plusDays(1);
        }
        return rateList;
    }

    private enum RateType {
        RISE, FALL, NO_CHANGE
    }
}