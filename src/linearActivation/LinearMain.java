package linearActivation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LinearMain {
    public static void main(String[] args) throws FileNotFoundException {
        String trainingDir = "trainingSet";
        String testDir = "testSet";
        double learningRate = 0.01;
        double acceptedErrMargin = 0.1;

        ArrayList<LinearLanguageObject> trainingSet = createSet(trainingDir);
        ArrayList<Neuron> neurons = new ArrayList<>();
        for(File languageDir : Objects.requireNonNull(new File(trainingDir).listFiles())){
            neurons.add(new Neuron(languageDir.getName(), learningRate, acceptedErrMargin));
        }
        for(Neuron neuron : neurons){
            neuron.learn(trainingSet);
//            System.out.println("Created neuron: "+ neuron); //Wyświetlanie utworzenia neuronu
        }

        //create matrix, f-...
        {
            System.out.println("");
            ArrayList<LinearLanguageObject> testSet = createSet(testDir);
            ArrayList<String[]> result = new ArrayList<>();
            HashMap<String, int[]> accuracy = new HashMap<>();
            for(LinearLanguageObject languageObject : testSet){
                result.add(new String[]{languageObject.getLanguageName(), testLanguageObject(neurons, languageObject)});
            }
            for (Neuron neuron : neurons) {
                System.out.print("  "+neuron.getLanguage());
            }
            System.out.println("");
            for (Neuron neuron : neurons){
                int[] lineOfMatrix = new int[neurons.size()];
                Arrays.fill(lineOfMatrix, 0);
                for(String[] s : result){
                    if(s[0].equals(neuron.getLanguage())){
                        for (int i = 0; i < neurons.size(); i++) {
                            if(s[1].equals(neurons.get(i).getLanguage())){
                                lineOfMatrix[i]++;
                            }
                        }
                    }
                }
                System.out.print(neuron.getLanguage());
                for (int j = 0; j < 20-neuron.getLanguage().length(); j++) {
                    System.out.print(" ");
                }
                for (int j = 0; j < neurons.size(); j++) {
                    System.out.print(" "+lineOfMatrix[j]);
                }
                System.out.println();
                accuracy.put(neuron.getLanguage(), lineOfMatrix);
            }
            //print accuracy
            System.out.println("");
            for(Neuron neuron : neurons){
                int sumOfRow = 0;
                int sumOfLine = Arrays.stream(accuracy.get(neuron.getLanguage())).sum();
                for(int i=0;i<neurons.size();i++){
                    sumOfRow += accuracy.get(neurons.get(i).getLanguage())[neurons.indexOf(neuron)];
                }

                System.out.println(neuron.getLanguage()+
                        " Dokładność: "+accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]+"/"+ Arrays.stream(testSet.toArray()).filter(a -> a.toString().equals(neuron.getLanguage())).count()+
                        " Precyzja: "+accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]/sumOfRow+
                        " Pełność: "+accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]/sumOfLine+
                        " F-miara: "+(2 * accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]/sumOfRow * accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]/sumOfLine)/( (accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]/sumOfRow) + (accuracy.get(neuron.getLanguage())[neurons.indexOf(neuron)]/sumOfLine))
                );
            }
        }

        System.out.println("");
        System.out.println("Czy chcesz manualnie sprawdzić tekst? (Napisz t/n)");
        Scanner scanner = new Scanner(System.in);
        String responce = scanner.nextLine();
        if(responce.equals("t")){
            System.out.println("Wpisz tekst i zakończ pisząć 'end'");
            LinearLanguageObject languageObject = languageObjectFromSystemIn();
            System.out.println("Program podejrzewa że wpisałeś tekst w języku: "+testLanguageObject(neurons, languageObject));
        }

    }

    public static ArrayList<LinearLanguageObject> createSet(String dirOfLanguageDirs) throws FileNotFoundException {
        ArrayList<LinearLanguageObject> trainingSet = new ArrayList<>();
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
                trainingSet.add(new LinearLanguageObject(letterDistribution, languageDir.getName()));
            }
        }
        return trainingSet;
    }

    public static String testLanguageObject(ArrayList<Neuron> neurons, LinearLanguageObject languageObject){
        double max = 0;
        String language = "";
//        System.out.print("Testing "+languageObject.getLanguageName()+": "); //Wyświetlanie testu
        for (Neuron neuron : neurons) {
            double x = neuron.test(languageObject);
//            System.out.print(neuron.getLanguage()+" "+x+" ");
            if (max < x) {
                max = x;
                language = neuron.getLanguage();
            }
        }
//        System.out.println("-> "+language);
        return language;
    }

    public static LinearLanguageObject languageObjectFromSystemIn(){
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
        return new LinearLanguageObject(letterDistribution, "");
    }
}
