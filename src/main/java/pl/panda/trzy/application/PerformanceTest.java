package pl.panda.trzy.application;

public class PerformanceTest {
    private static int NUMBER_OF_TESTS = 1000;

	public static void main(String[] args) {
        String statAnalysisSummary = testStatAnalysis();
        String distributionAnalysisSummary = testDistributionAnalysis();
        String sessionsAnalysisSummary = testSessionsAnalysis();

        System.out.println(statAnalysisSummary);
        System.out.println(distributionAnalysisSummary);
        System.out.println(sessionsAnalysisSummary);
	}

    private static String testStatAnalysis() {
		String testCurrency = "EUR";
		long start = System.currentTimeMillis();

		for(int i = 0; i < NUMBER_OF_TESTS; i++) {
			Application.performStatAnalysis(testCurrency);
		}

		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		String summary = String.format("Performance test: %d executions of static analysis took %dms.%n", NUMBER_OF_TESTS, elapsed);
		return summary;
    }

    private static String testDistributionAnalysis() {
		String testCurrency1 = "EUR";
        String testCurrency2 = "USD";
		long start = System.currentTimeMillis();

		for(int i = 0; i < NUMBER_OF_TESTS; i++) {
			Application.performDistributionAnalysis(testCurrency1, testCurrency2);
		}

		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		String summary = String.format("Performance test: %d executions of distribution analysis took %dms.%n", NUMBER_OF_TESTS, elapsed);
		return summary;
    }

    private static String testSessionsAnalysis() {
		String testCurrency = "EUR";
		long start = System.currentTimeMillis();

		for(int i = 0; i < NUMBER_OF_TESTS; i++) {
			Application.performSessionsAnalysis(testCurrency);
		}

		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		String summary = String.format("Performance test: %d executions of sessions analysis took %dms.%n", NUMBER_OF_TESTS, elapsed);
		return summary;
    }
}
