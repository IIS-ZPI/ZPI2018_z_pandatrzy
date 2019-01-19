package pl.panda.trzy.util;

import pl.panda.trzy.analysis.AbstractOperation;
import pl.panda.trzy.analysis.SessionsCount;
import pl.panda.trzy.analysis.StatAnalysis;
import pl.panda.trzy.nbp.PeriodType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.*;

public class CSVWriter {
    private static final char DEFAULT_SEPARATOR = '|';

    private static final List<String> EMPTY_LINE = new ArrayList<>();

    private static final String DEFAULT_CSV_SAVE_PATH = LocalDateTime.now().toString()
            .replaceAll(":", "-") + ".csv";

    private static final String DEFAULT_SESSION_ANALYSIS_FILE_PREFIX = "sessions_";

    private static final String DEFAULT_STAT_ANALYSIS_FILE_PREFIX = "stat_";

    private static final String DEFAULT_DISTRIBUTION_ANALYSIS_FILE_PREFIX = "distribution_";

    private static final String NEW_LINE = "\n";

    private static final String SEP = "sep=|";

    public static void writeSessionAnalysisToCSV(Map<PeriodType, SessionsCount> sessionsCount) throws IOException{
        writeOperationToCSV(sessionsCount, DEFAULT_SESSION_ANALYSIS_FILE_PREFIX + DEFAULT_CSV_SAVE_PATH);
    }

    public static void writeStatAnalysisToCSV(Map<PeriodType, StatAnalysis> statAnalysisMap) throws IOException{
        writeOperationToCSV(statAnalysisMap, DEFAULT_STAT_ANALYSIS_FILE_PREFIX + DEFAULT_CSV_SAVE_PATH);
    }

    public static void writeDistributionAnalysisToCSV(Map<Integer, Double> distributionMap) throws IOException {
        FileWriter writer = new FileWriter(DEFAULT_DISTRIBUTION_ANALYSIS_FILE_PREFIX + DEFAULT_CSV_SAVE_PATH);
        writeLine(writer, Collections.singletonList(SEP));
        //no lambda because we want potential exceptions to bubble up
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : distributionMap.entrySet()) {
            keys.add(entry.getKey().toString());
            values.add(entry.getValue().toString());
        }
        writeLine(writer, keys);
        writeLine(writer, values);
        writer.flush();
        writer.close();
    }

    private static void writeOperationToCSV(Map<PeriodType, ? extends AbstractOperation> operationMap, String csvFilePath) throws IOException{
        FileWriter writer = new FileWriter(csvFilePath);

        writeLine(writer, Collections.singletonList(SEP));
        //no lambda because we want potential exceptions to bubble up
        for(PeriodType key : operationMap.keySet()){
            writeLine(writer, Collections.singletonList(key.name()));
            List<String> headers = new ArrayList<>(operationMap.get(key).getHeaders());
            writeLine(writer, headers);

            List<String> values = new ArrayList<>(operationMap.get(key).getValuesList());
            writeLine(writer, values);

            writeLine(writer, EMPTY_LINE);
        }

        writer.flush();
        writer.close();
    }

    private static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    private static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append(NEW_LINE);
        String result = sb.toString();

        w.append(result);


    }
}
