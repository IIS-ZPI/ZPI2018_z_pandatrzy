package pl.panda.trzy.util;

import pl.panda.trzy.analysis.SessionsCount;
import pl.panda.trzy.nbp.PeriodType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CSVWriter {
    private static final char DEFAULT_SEPARATOR = ',';

    private static final List<String> EMPTY_LINE = new ArrayList<>();

    private static final String DEFAULT_CSV_SAVE_PATH = "sessions_" + LocalDateTime.now().toString()
            .replaceAll(":", "-") + ".csv";

    public static void writeSessionAnalysisToCSV(Map<PeriodType, SessionsCount> sessionsCount) throws IOException{
        String csvFile = DEFAULT_CSV_SAVE_PATH;
        FileWriter writer = new FileWriter(csvFile);

        writeLine(writer, Collections.singletonList("sep=,"));
        //no lambda because we want potential exceptions to bubble up
        for(PeriodType key : sessionsCount.keySet()){
            writeLine(writer, Collections.singletonList(key.name()));
            List<String> headers = new ArrayList<>(sessionsCount.get(key).getHeaders());
            writeLine(writer, headers);

            List<String> values = new ArrayList<>(sessionsCount.get(key).getValuesList());
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
        sb.append("\n");
        w.append(sb.toString());


    }
}
