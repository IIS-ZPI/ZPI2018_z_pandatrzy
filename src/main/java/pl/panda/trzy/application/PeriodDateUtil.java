package pl.panda.trzy.application;

import pl.panda.trzy.nbp.PeriodType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PeriodDateUtil {

    public static Date getPastDate(LocalDateTime date, PeriodType periodType) {
        return Date.from(getLocalPastDate(date, periodType).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime getLocalPastDate(LocalDateTime date, PeriodType periodType) {
        LocalDateTime datePast;
        switch (periodType) {
            case WEEK: {
                datePast = date.minusWeeks(1L);
                break;
            }
            case TWO_WEEKS: {
                datePast = date.minusWeeks(2L);
                break;
            }
            case MONTH: {
                datePast = date.minusMonths(1L);
                break;
            }
            case QUARTER: {
                datePast = date.minusMonths(3L);
                break;
            }
            case HALF_OF_YEAR: {
                datePast = date.minusMonths(6L);
                break;
            }
            case YEAR: {
                datePast = date.minusYears(1L);
                break;
            }
            default: {
                datePast = date;
            }
        }
        return datePast;
    }
}
