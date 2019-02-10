package pl.panda.trzy.application;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import pl.panda.trzy.analysis.AnalysisApi;
import pl.panda.trzy.analysis.AnalysisApiImpl;
import pl.panda.trzy.analysis.StatAnalysis;
import pl.panda.trzy.nbp.NbpApi;
import pl.panda.trzy.nbp.NbpApiImp;
import pl.panda.trzy.nbp.NbpResponse;
import pl.panda.trzy.nbp.PeriodType;
import pl.panda.trzy.util.CSVWriter;

public class PerformanceTest {

	public static void main(String[] args) {
		String testCurrency = "EUR";
		long start = System.currentTimeMillis();

		for(int i = 0; i < 1000; i++) {
			performStatAnalysis(testCurrency);
		}

		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		System.out.printf("Performance test: 1000 executions of static analysis took %dms.%n", elapsed);
	}

    private static void performStatAnalysis(String currency) {
        NbpApi nbpApi = new NbpApiImp();
        AnalysisApi analysisApi = new AnalysisApiImpl();
        NbpResponse response = nbpApi.getExchangeRate(currency, PeriodType.YEAR);
        Map<PeriodType, StatAnalysis> analysis = analysisApi.performStatAnalysis(new LinkedList<>(response.getRates()));

        try {
            CSVWriter.writeStatAnalysisToCSV(analysis);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wystąpił błąd w trakcie zapisu wyników.");
        }
        System.out.println("Wyniki zapisane do pliku");
    }
}
