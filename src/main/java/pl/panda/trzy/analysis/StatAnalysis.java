package pl.panda.trzy.analysis;

import java.util.ArrayList;
import java.util.List;

public class StatAnalysis extends AbstractOperation {

    private static final String MODE_SEPARATOR = ", ";
    private static final String MEDIAN_HEADER = "MEDIAN";
    private static final String MODES_HEADER = "MODES";
    private static final String STANDARD_DEVIATION_HEADER = "STANDARD_DEVIATION";
    private static final String COEFFICIENT_HEADER = "COEFFICIENT";

    private double median;
    private double[] modes;
    private double standardDeviation;
    private double coefficient;

    StatAnalysis(double median, double[] modes, double standardDeviation, double coefficient) {
        this.median = median;
        this.modes = modes;
        this.standardDeviation = standardDeviation;
        this.coefficient = coefficient;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double[] getModes() {
        return modes;
    }

    public void setModes(double[] modes) {
        this.modes = modes;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public List<String> getValuesList(){
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(this.median));
        StringBuilder sb = new StringBuilder();
        for (double mode : this.modes) {
            sb.append(mode).append(MODE_SEPARATOR);
        }
        list.add(sb.toString());
        list.add(String.valueOf(this.standardDeviation));
        list.add(String.valueOf(this.coefficient));
        return list;
    }

    @Override
    public List<String> getHeaders(){
        List<String> list = new ArrayList<>();
        list.add(MEDIAN_HEADER);
        list.add(MODES_HEADER);
        list.add(STANDARD_DEVIATION_HEADER);
        list.add(COEFFICIENT_HEADER);
        return list;
    }
}
