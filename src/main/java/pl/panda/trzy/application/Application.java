package pl.panda.trzy.application;

import pl.panda.trzy.analysis.AnalysisApi;
import pl.panda.trzy.analysis.AnalysisApiImpl;
import pl.panda.trzy.analysis.SessionsCount;
import pl.panda.trzy.analysis.StatAnalysis;
import pl.panda.trzy.nbp.NbpApi;
import pl.panda.trzy.nbp.NbpApiImp;
import pl.panda.trzy.nbp.NbpResponse;
import pl.panda.trzy.nbp.PeriodType;
import pl.panda.trzy.util.CSVWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) throws IOException {
        AnalysisType type = null;
        while (type == null) {
            type = determineAnalysisType(type);
        }
        switch (type) {
            case SESSIONS: {
                String currency = getCurrency();
                performSessionsAnalysis(currency);
                break;
            }
            case STATISTICAL_MEASURES: {
                String currency = getCurrency();
                performStatAnalysis(currency);
                break;
            }
            case DISTRIBUTION: {
                String currency1 = getCurrency();
                String currency2 = getCurrency();
                performDistributionAnalysis(currency1, currency2);
                break;
            }
            case EXIT: {
                System.out.println("Wychodzę...");
                break;
            }
            default: {
                System.out.println("Niewspierana analiza");
            }
        }

    }

    private static void performDistributionAnalysis(String currency1, String currency2) {
        NbpApi nbpApi = new NbpApiImp();
        AnalysisApi analysisApi = new AnalysisApiImpl();
        Set<String> currencies = Stream.of(currency1, currency2).collect(Collectors.toSet());
        Map<String, NbpResponse> responseMap = nbpApi.getExchangeRates(currencies, PeriodType.YEAR);
        //analysisApi.performDistributionAnalysis(responseMap); //todo - nie wiem jak się to liczy...
        //todo zapis do pliku
    }

    private static void performStatAnalysis(String currency) {
        NbpApi nbpApi = new NbpApiImp();
        AnalysisApi analysisApi = new AnalysisApiImpl();
        NbpResponse response = nbpApi.getExchangeRate(currency, PeriodType.YEAR);
        Map<PeriodType, StatAnalysis> analysis = analysisApi.performStatAnalysis(new LinkedList<>(response.getRates()));
        //todo zapis do pliku

    }

    private static void performSessionsAnalysis(String currency) {
        NbpApi nbpApi = new NbpApiImp();
        AnalysisApi analysisApi = new AnalysisApiImpl();
        NbpResponse response = nbpApi.getExchangeRate(currency, PeriodType.YEAR);
        Map<PeriodType, SessionsCount> sessionsCount = analysisApi.performSessionAnalysis(new LinkedList<>(response.getRates()));
        try {
            CSVWriter.writeSessionAnalysisToCSV(sessionsCount);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wystąpił błąd w trakcie zapisu wyników.");
        }
        System.out.println("Wyniki zapisane do pliku");
    }

    private static String getCurrency() throws IOException {
        System.out.println("Podaj walutę:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine().toUpperCase();
    }

    private static AnalysisType determineAnalysisType(AnalysisType type) throws IOException {
        System.out.println("Podaj rodzaj analizy:\n1 - wyznaczenie ilości sesji wzrostowych, spadkowych i bez zmian");
        System.out.println("2 - miary statystyczne: miediana, dominanta, odchylenie standardowe i współczynnik zmienności");
        System.out.println("3 - rozkład częstości zmian tygodniowych");
        System.out.println("4 - exit");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        switch (line) {
            case "1": {
                type = AnalysisType.SESSIONS;
                break;
            }
            case "2": {
                type = AnalysisType.STATISTICAL_MEASURES;
                break;
            }
            case "3": {
                type = AnalysisType.DISTRIBUTION;
                break;
            }
            case "4": {
                type = AnalysisType.EXIT;
                break;
            }
            default: {
                System.out.println("Nieprawidłowy wybór");
            }
        }
        return type;
    }
}
