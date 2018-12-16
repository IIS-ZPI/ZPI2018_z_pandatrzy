package pl.panda.trzy.nbp;

import java.util.Map;
import java.util.Set;

public interface NbpApi {
    NbpResponse getExchangeRate(String currency, PeriodType periodType);
    Map<String, NbpResponse> getExchangeRates(Set<String> currency, PeriodType periodType);
}
