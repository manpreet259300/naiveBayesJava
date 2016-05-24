package Classification;

import ProcessingFolder.FetchingSubjects;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by Manpreet Gandhi on 5/22/2016.
 */

public class MyNaiveBayesBuild {
    private static final String SPAM = "C:\\Users\\Manpreet Gandhi\\Desktop\\Lab2-CS286\\spam";
    private static final String HAM = "C:\\Users\\Manpreet Gandhi\\Desktop\\Lab2-CS286\\easy_ham";

    public static void main(String[] args) {

        //properties for saving the map and loading the map
        Properties properties = new Properties();
        int trainingPercentage = 70;

        //get two args for spam and ham files
        ArrayList<String> spamSubjects = new ArrayList<>();
        ArrayList<String> hamSubjects = new ArrayList<>();
        List<String> spamSubJectsTest = new ArrayList<>();
        List<String> hamSubjectsTest = new ArrayList<>();
        HashMap<String, Integer> spamSubjectMap = new HashMap<>();
        HashMap<String, Integer> hamSubjectMap = new HashMap<>();

        FetchingSubjects fs = new FetchingSubjects();

        //fetching subject from input files
        spamSubjects = fs.returnSubjects(SPAM);
        hamSubjects = fs.returnSubjects(HAM);

        // getting size of spam and ham data sets
        float spamDoc = spamSubjects.size();
        float hamDoc = hamSubjects.size();

        int spamIndex = Math.round(spamDoc * trainingPercentage / 100);
        int hamIndex = Math.round(hamDoc * trainingPercentage / 100);

        //test data creation
        spamSubJectsTest = spamSubjects.subList(spamIndex, spamSubjects.size());
        hamSubjectsTest = hamSubjects.subList(hamIndex, hamSubjects.size());

        //calculating probability of having a spam
        float totalDoc = spamIndex + hamIndex;
        float pSpam = spamIndex / totalDoc;
        float pHam = hamIndex / totalDoc;


        //get Map of subjects of the files
        spamSubjectMap = fs.getSubjectMap(spamSubjects.subList(0, spamIndex));
        hamSubjectMap = fs.getSubjectMap(hamSubjects.subList(0, hamIndex));

        int spamSize = spamSubjectMap.size();
        int hamSize = hamSubjectMap.size();

        HashMap<String, Integer> map3 = new HashMap<>();
        map3.putAll(spamSubjectMap);
        map3.putAll(hamSubjectMap);

        HashMap<String, String> finalMap = new HashMap<>();

        for (String key : map3.keySet()) {
            String value = "";
            if (spamSubjectMap.containsKey(key) && hamSubjectMap.containsKey(key)) {
                value = calculateProbability(spamSubjectMap.get(key), spamSize, map3.size())
                        + "," + calculateProbability(hamSubjectMap.get(key), hamSize, map3.size());
            } else if (!spamSubjectMap.containsKey(key) && hamSubjectMap.containsKey(key)) {
                value = "notPresent," + calculateProbability(hamSubjectMap.get(key), hamSize, map3.size());
            } else if (spamSubjectMap.containsKey(key) && !hamSubjectMap.containsKey(key)) {
                value = calculateProbability(spamSubjectMap.get(key), spamSize, map3.size()) + ",notPresent";
            } else {
                value = "notPresent,notPresent";
            }

            finalMap.put(key, value);
        }

        finalMap.put("TotalProbabilityOfSpam", Float.toString(pSpam));
        finalMap.put("TotalProbabilityOfHam", Float.toString(pHam));

        //
        properties.putAll(finalMap);
        try {

            properties.store(new FileOutputStream("Model.properties"), null);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<String> spamResult = classifyResults(pSpam, pHam, spamSubJectsTest, finalMap, "SPAM" );
        ArrayList<String> hamResult = classifyResults(pSpam, pHam, spamSubJectsTest, finalMap, "HAM" );
        hamResult.addAll(spamResult);

        int truePos = 0;
        int falsePos = 0;
        int falseNeg = 0;
        int trueNeg = 0;

//        True positive = correctly identified
//        False positive = incorrectly identified
//        True negative = correctly rejected
//        False negative = incorrectly rejected
        for(String res: hamResult)
        {

            String[] resSplit = res.split(",");
            System.out.println(resSplit[0]  + " - " + resSplit[1]);
            if (resSplit[0].equals("SPAM"))
            {
                if(resSplit[1].equals("SPAM"))
                {
                    truePos++;
                }
                else {
                    falsePos++;
                }
            }
            else{
                if(resSplit[1].equals("SPAM"))
                {
                    falseNeg++;
                }
                else
                {
                    trueNeg++;
                }
            }
        }
        System.out.println("True Positive : " + truePos);
        System.out.println("False Positive : " + falsePos);
        System.out.println("False Negative : " + falseNeg);
        System.out.println("True Negative : " + trueNeg);

        float accuracy = (float)(truePos + trueNeg) / (float) (truePos + falsePos + falseNeg + trueNeg);
        System.out.println("Accuracy : " + accuracy);

    }

    public static String calculateProbability(int termInClass, int hamSpamsize, int totalCorpusSize) {
        float lambda = 100.0F;
        float probability = 0.0F;
        probability = (lambda + termInClass) / (float) (hamSpamsize + totalCorpusSize);
        return Float.toString(probability);
    }

    public static ArrayList<String> classifyResults(float probOfSpam,
                                                    float probOfHam,
                                                    List<String> inputSubjects,
                                                    HashMap<String, String> Model,
                                                    String type) {
        ArrayList<String> result = new ArrayList<>();

        for (String sub : inputSubjects) {
            String output ="";
            String classify = "";
            String[] subSplit = sub.split(" ");
            float resultHam = probOfHam;
            float resultSpam = probOfSpam;
            for (int i = 0; i < subSplit.length; i++) {
                float pSpam = 1.0F;
                float pHam = 1.0F;

                if (Model.containsKey(subSplit[i])) {
                    String[] Value = Model.get(subSplit[i]).split(",");
                    if (!Value[0].contains("notPresent")) {
                        pSpam = Float.parseFloat(Value[0]);
                    } else {
                        pSpam = 1.0F;
                    }
                    if (!Value[1].contains("notPresent")) {
                        pHam = Float.parseFloat(Value[1]);
                    } else {
                        pHam = 1.0F;
                    }
                }
                resultHam *= pSpam;
                resultSpam *= pHam;
            }

            if (resultHam >= resultSpam)
            {
                output  = "HAM";
            }
            else {
                output = "SPAM";
            }

            result.add(type + "," + output);
        }
        return  result;
    }
}
