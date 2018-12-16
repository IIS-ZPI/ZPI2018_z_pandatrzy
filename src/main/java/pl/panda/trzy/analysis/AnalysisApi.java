package pl.panda.trzy.analysis;

import pl.panda.trzy.nbp.Rate;

import java.util.List;

public interface AnalysisApi {

    /**
     * Zwraca liczbę sesji wzrostowych, spadkowych i bez zmian dla listy kursów waluty
     * @param rateList lista kursów waluty
     * @return
     */
    SessionsCount countSessions(List<Rate> rateList);
}
