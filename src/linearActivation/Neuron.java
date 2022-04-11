package linearActivation;

import java.util.ArrayList;
import java.util.Arrays;

public class Neuron {
    private double[] weights = new double[26];
    private final String language;
    private final double learningRate;
    private int iteration = 0;
    private double acceptedErrMargin;


    public Neuron(String language, double learningRate, double acceptedErrMargin) {
        Arrays.fill(weights, 0);
        this.language = language;
        this.learningRate = learningRate;
        this.acceptedErrMargin = acceptedErrMargin;
    }

    public void learn(ArrayList<LinearLanguageObject> trainingSet) {
        double err = 0;
        for(LinearLanguageObject languageObject : trainingSet){
            double wtx = 0;
            for (int i = 0; i < weights.length; i++) {
                wtx += weights[i]*languageObject.getLetterDistribution()[i];
            }
            double z = 1/(1+Math.exp((-1)*wtx));
            int expectedResult = 0;
            if(languageObject.getLanguageName().equals(language)){
                expectedResult = 1;
            }
            for (int i = 0; i < weights.length; i++) {
                weights[i] += learningRate*(expectedResult-z)*z*(1-z)*languageObject.getLetterDistribution()[i];
            }
            err += Math.pow(expectedResult-z,2);
        }
        err = err/2;
        if(err>acceptedErrMargin && iteration<1000){
            iteration++;
            learn(trainingSet);
        }
    }

    public double test(LinearLanguageObject languageObject){
        double wtx = 0;
        for (int i = 0; i < weights.length; i++) {
            wtx += weights[i]*languageObject.getLetterDistribution()[i];
        }
        return 1/(1+Math.exp((-1)*wtx));
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return language+" "+Arrays.toString(weights);
    }
}
