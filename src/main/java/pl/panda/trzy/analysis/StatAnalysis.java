package pl.panda.trzy.analysis;

public class StatAnalysis {

    private double median;
    private double[] modes;
    private double standardDeviation;
    private double coefficient;

    public StatAnalysis(double median, double[] modes, double standardDeviation, double coefficient) {
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
}
