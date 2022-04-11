package binaryActivation;

import java.util.ArrayList;
import java.util.Arrays;

public class Perceptron {
    private double[] weights = new double[26];
    private final String language;
    private final double learningRate;
    private int iteration = 0;


    public Perceptron(String language, double learningRate) {
        Arrays.fill(weights, 0);
        this.language = language;
        this.learningRate = learningRate;
    }

    public void learn(ArrayList<BinaryLanguageObject> trainingSet) {
        for(BinaryLanguageObject languageObject : trainingSet){
            double sum = 0;
            for (int i = 0; i < weights.length; i++) {
                sum += weights[i]*languageObject.getLetterDistribution()[i];
            }
            if(sum > 0 && languageObject.getLanguageName().equals(language) && iteration<1000){
                for (int i = 0; i < weights.length; i++) {
                    weights[i] -= learningRate*languageObject.getLetterDistribution()[i];
                }
                iteration++;
                learn(trainingSet);
                return;
            }
            if(sum <= 0 && !languageObject.getLanguageName().equals(language) && iteration<1000){
                for (int i = 0; i < weights.length; i++) {
                    weights[i] += learningRate*languageObject.getLetterDistribution()[i];
                }
                iteration++;
                learn(trainingSet);
                return;
            }
        }
    }

    public boolean test(BinaryLanguageObject languageObject){
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i]*languageObject.getLetterDistribution()[i];
        }
        return sum <= 0;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return language+" "+Arrays.toString(weights)+" I:"+iteration;
    }
}
