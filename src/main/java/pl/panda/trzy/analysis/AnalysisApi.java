package pl.panda.trzy.analysis;

import pl.panda.trzy.nbp.NbpResponse;
import pl.panda.trzy.nbp.PeriodType;
import pl.panda.trzy.nbp.Rate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;

public interface AnalysisApi {

    /**
     * Zwraca liczbę sesji wzrostowych, spadkowych i bez zmian dla listy kursów waluty
     * @param rateList lista kursów waluty
     * @return
     */
    SessionsCount countSessions(LinkedList<Rate> rateList);

    /**
     * Mapuje liczbę sesji wzrostowych, spadkowych i bez zmian dla listy kursów walut
     * na wszystkie typy okresów, pod warunkiem że przekazano w parametrze listę całoroczną
     * @param rateList
     * @return
     */
    Map<PeriodType, SessionsCount> performSessionAnalysis(LinkedList<Rate> rateList);

    /**
     * Odfiltrowuje kursy walut dla podanego okresu z listy podanej jako parametr wejściowy. Przykładowo z listy kursów
     * dla całego roku możemy odfiltrować dwa tygodnie
     * @param rateList
     * @param date
     * @param periodType
     * @return
     */
    LinkedList<Rate> filterRatesForPeriod(LinkedList<Rate> rateList, LocalDateTime date, PeriodType periodType);

    /**
     * Oblicza wszystie miary statystyczne dla podanej listy kursów waluty za wszystkie okresy
     * @param rates
     * @return
     */
    Map<PeriodType, StatAnalysis> performStatAnalysis(LinkedList<Rate> rates);

    /**
     * Zwraca mapę rozkładu częstości zmian tygodniowych dla wybranych 2 walut. Każdy element na mapie to wpółczynnik
     * z kolejnego tygodnia
     * @param responseMap
     * @return
     */
    Map<Integer, Double> performDistributionAnalysis(Map<String, NbpResponse> responseMap);
}
