package binaryActivation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinaryMain {
    public static void main(String[] args) throws FileNotFoundException {
        String trainingDir = "trainingSet";
        String testDir = "testSet";
        double learningRate = 1;

        ArrayList<BinaryLanguageObject> trainingSet = createSet(trainingDir);
        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        for(File languageDir : Objects.requireNonNull(new File(trainingDir).listFiles())){
            perceptrons.add(new Perceptron(languageDir.getName(), learningRate));
        }
        for(Perceptron perceptron : perceptrons){
            perceptron.learn(trainingSet);
            System.out.println("Created perceptron: "+ perceptron); //Wyświetlanie utworzenia perceptronu
        }

        //create matrix, f-...
        {
            System.out.println("");
            ArrayList<BinaryLanguageObject> testSet = createSet(testDir);
            ArrayList<String[]> result = new ArrayList<>();
            HashMap<String, int[]> accuracy = new HashMap<>();
            for(BinaryLanguageObject languageObject : testSet){
                result.add(new String[]{languageObject.getLanguageName(), testLanguageObject(perceptrons, languageObject)});
            }
            for (Perceptron perceptron : perceptrons) {
                System.out.print("  "+ perceptron.getLanguage());
            }
            System.out.println("");
            for (Perceptron perceptron : perceptrons){
                int[] lineOfMatrix = new int[perceptrons.size()];
                Arrays.fill(lineOfMatrix, 0);
                for(String[] s : result){
                    if(s[0].equals(perceptron.getLanguage())){
                        for (int i = 0; i < perceptrons.size(); i++) {
                            if(s[1].equals(perceptrons.get(i).getLanguage())){
                                lineOfMatrix[i]++;
                            }
                        }
                    }
                }
                System.out.print(perceptron.getLanguage());
                for (int j = 0; j < 20- perceptron.getLanguage().length(); j++) {
                    System.out.print(" ");
                }
                for (int j = 0; j < perceptrons.size(); j++) {
                    System.out.print(" "+lineOfMatrix[j]);
                }
                System.out.println();
                accuracy.put(perceptron.getLanguage(), lineOfMatrix);
            }
            //print accuracy
            System.out.println("");
            for(Perceptron perceptron : perceptrons){
                double sumOfRow = 0;
                double sumOfLine = Arrays.stream(accuracy.get(perceptron.getLanguage())).sum();
                for(int i = 0; i< perceptrons.size(); i++){
                    sumOfRow += accuracy.get(perceptrons.get(i).getLanguage())[perceptrons.indexOf(perceptron)];
                }

                System.out.println(perceptron.getLanguage()+
                        " Dokładność: "+accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]+"/"+ Arrays.stream(testSet.toArray()).filter(a -> a.toString().equals(perceptron.getLanguage())).count()+
                        " Precyzja: "+accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]/sumOfRow+
                        " Pełność: "+accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]/sumOfLine+
                        " F-miara: "+(2 * accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]/sumOfRow * accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]/sumOfLine)/( (accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]/sumOfRow) + (accuracy.get(perceptron.getLanguage())[perceptrons.indexOf(perceptron)]/sumOfLine))
                );
            }
        }

        System.out.println("");
        System.out.println("Czy chcesz manualnie sprawdzić tekst? (Napisz t/n)");
        Scanner scanner = new Scanner(System.in);
        String responce = scanner.nextLine();
        if(responce.equals("t")){
            System.out.println("Wpisz tekst i zakończ pisząć 'end'");
            BinaryLanguageObject languageObject = languageObjectFromSystemIn();
            System.out.println("Program podejrzewa że wpisałeś tekst w języku: "+testLanguageObject(perceptrons, languageObject));
        }

    }

    public static ArrayList<BinaryLanguageObject> createSet(String dirOfLanguageDirs) throws FileNotFoundException {
        ArrayList<BinaryLanguageObject> trainingSet = new ArrayList<>();
        for(File languageDir : Objects.requireNonNull(new File(dirOfLanguageDirs).listFiles())){
            for(File file : Objects.requireNonNull(languageDir.listFiles())){
                double[] letterDistribution = new double[26];
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()){
                    for(char c : scanner.nextLine().replaceAll("[^A-Za-z]", "").toLowerCase().toCharArray()){
                        switch (c) {
                            case 'a' -> letterDistribution[0]++;
                            case 'b' -> letterDistribution[1]++;
                            case 'c' -> letterDistribution[2]++;
                            case 'd' -> letterDistribution[3]++;
                            case 'e' -> letterDistribution[4]++;
                            case 'f' -> letterDistribution[5]++;
                            case 'g' -> letterDistribution[6]++;
                            case 'h' -> letterDistribution[7]++;
                            case 'i' -> letterDistribution[8]++;
                            case 'j' -> letterDistribution[9]++;
                            case 'k' -> letterDistribution[10]++;
                            case 'l' -> letterDistribution[11]++;
                            case 'm' -> letterDistribution[12]++;
                            case 'n' -> letterDistribution[13]++;
                            case 'o' -> letterDistribution[14]++;
                            case 'p' -> letterDistribution[15]++;
                            case 'q' -> letterDistribution[16]++;
                            case 'r' -> letterDistribution[17]++;
                            case 's' -> letterDistribution[18]++;
                            case 't' -> letterDistribution[19]++;
                            case 'u' -> letterDistribution[20]++;
                            case 'v' -> letterDistribution[21]++;
                            case 'w' -> letterDistribution[22]++;
                            case 'x' -> letterDistribution[23]++;
                            case 'y' -> letterDistribution[24]++;
                            case 'z' -> letterDistribution[25]++;
                        }
                    }
                }
                int letterCount = 0;
                for(Double d : letterDistribution){
                    letterCount += d;
                }
                for(int i=0; i<letterDistribution.length; i++){
                    letterDistribution[i] = letterDistribution[i]/letterCount;
                }
                trainingSet.add(new BinaryLanguageObject(letterDistribution, languageDir.getName()));
            }
        }
        return trainingSet;
    }

    public static String testLanguageObject(ArrayList<Perceptron> perceptrons, BinaryLanguageObject languageObject){
//        System.out.print("Testing "+languageObject.getLanguageName()+": "); //Wyświetlanie testu
        for (Perceptron perceptron : perceptrons) {
            if(perceptron.test(languageObject)){
                //        System.out.println("-> "+perceptron.getLanguage());
                return perceptron.getLanguage();
            }
        }
        return "unable to identify";
    }

    public static BinaryLanguageObject languageObjectFromSystemIn(){
        Scanner scanner = new Scanner(System.in);
        double[] letterDistribution = new double[26];
        String line = scanner.nextLine();
        while (!line.equals("end")){
            for(char c : line.replaceAll("[^A-Za-z]", "").toLowerCase().toCharArray()){
                switch (c) {
                    case 'a' -> letterDistribution[0]++;
                    case 'b' -> letterDistribution[1]++;
                    case 'c' -> letterDistribution[2]++;
                    case 'd' -> letterDistribution[3]++;
                    case 'e' -> letterDistribution[4]++;
                    case 'f' -> letterDistribution[5]++;
                    case 'g' -> letterDistribution[6]++;
                    case 'h' -> letterDistribution[7]++;
                    case 'i' -> letterDistribution[8]++;
                    case 'j' -> letterDistribution[9]++;
                    case 'k' -> letterDistribution[10]++;
                    case 'l' -> letterDistribution[11]++;
                    case 'm' -> letterDistribution[12]++;
                    case 'n' -> letterDistribution[13]++;
                    case 'o' -> letterDistribution[14]++;
                    case 'p' -> letterDistribution[15]++;
                    case 'q' -> letterDistribution[16]++;
                    case 'r' -> letterDistribution[17]++;
                    case 's' -> letterDistribution[18]++;
                    case 't' -> letterDistribution[19]++;
                    case 'u' -> letterDistribution[20]++;
                    case 'v' -> letterDistribution[21]++;
                    case 'w' -> letterDistribution[22]++;
                    case 'x' -> letterDistribution[23]++;
                    case 'y' -> letterDistribution[24]++;
                    case 'z' -> letterDistribution[25]++;
                }
            }
            line = scanner.nextLine();
        }
        int letterCount = 0;
        for(Double d : letterDistribution){
            letterCount += d;
        }
        for(int i=0; i<letterDistribution.length; i++){
            letterDistribution[i] = letterDistribution[i]/letterCount;
        }
        return new BinaryLanguageObject(letterDistribution, "");
    }
}
