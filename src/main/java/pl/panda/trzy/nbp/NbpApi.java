package pl.panda.trzy.nbp;

import java.util.Map;
import java.util.Set;

public interface NbpApi {
    /**
     * Pobiera kurs pojedynczej waluty dla wybranego okresu
     * @param currency waluta
     * @param periodType wybrany okres
     * @return kurs waluty
     */
    NbpResponse getExchangeRate(String currency, PeriodType periodType);

    /**
     * Pobiera kursy dla wielu walut dla wybranego okresu. Działa wielowątkowo, kurs każdej waluty pobierany jest
     * w osobnym wątku
     * @param currencies kolekcja walut
     * @param periodType wybrany okres
     * @return mapa kursów walut
     */
    Map<String, NbpResponse> getExchangeRates(Set<String> currencies, PeriodType periodType);
}
