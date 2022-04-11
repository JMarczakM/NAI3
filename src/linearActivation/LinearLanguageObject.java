package linearActivation;

public class LinearLanguageObject {
    private double[] letterDistribution = new double[26];
    private final String languageName;

    public LinearLanguageObject(double[] letterDistribution, String languageName) {
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
