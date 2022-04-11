package binaryActivation;

public class BinaryLanguageObject {
    private double[] letterDistribution = new double[26];
    private final String languageName;

    public BinaryLanguageObject(double[] letterDistribution, String languageName) {
        this.letterDistribution = letterDistribution;
        this.languageName = languageName;
    }

    public double[] getLetterDistribution() {
        return letterDistribution;
    }

    public String getLanguageName() {
        return languageName;
    }

    @Override
    public String toString() {
        return languageName;
    }
}
