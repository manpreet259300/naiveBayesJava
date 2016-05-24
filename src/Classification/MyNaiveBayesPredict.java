package Classification;

import ProcessingFolder.FetchingSubjects;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Manpreet Gandhi on 5/22/2016.
 */
public class MyNaiveBayesPredict {

    public static void  main(String[]  args) {

        float TotalProbabilityOfHam = 0.0F;
        float TotalProbabilityOfSpam =0.0F;

        int counter =1;

        String Path = "C:\\Users\\Manpreet Gandhi\\Desktop\\Lab2-CS286\\inputSpam";
        Map<String, String> Model = new HashMap<String, String>();
        ArrayList<String> inputSubjects = new ArrayList<>();
        ArrayList<String> finalList = new ArrayList<>();

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("Model.properties"));
        } catch (Exception e) {
            System.out.println(e);
        }

        for (String key : properties.stringPropertyNames()) {
            Model.put(key, properties.get(key).toString());
        }

        TotalProbabilityOfHam = Float.parseFloat(Model.get("TotalProbabilityOfHam"));
        TotalProbabilityOfSpam = Float.parseFloat(Model.get("TotalProbabilityOfSpam"));

        FetchingSubjects fs = new FetchingSubjects();
        inputSubjects = fs.returnSubjects(Path);


        for (String sub : inputSubjects)
        {
            String classify = "";
            String[] subSplit = sub.split(" ");
            float resultHam = TotalProbabilityOfHam;
            float resultSpam = TotalProbabilityOfSpam;
            for(int i = 0; i < subSplit.length; i++)
            {
                float pSpam =1.0F;
                float pHam  =1.0F;

                if(Model.containsKey(subSplit[i]))
                {
                    String[] Value = Model.get(subSplit[i]).split(",");
                    if(!Value[0].contains("notPresent"))
                    {
                        pSpam = Float.parseFloat(Value[0]);
                    }
                    else {
                        pSpam =1.0F;
                    }
                    if (!Value[1].contains("notPresent"))
                    {
                        pHam = Float.parseFloat(Value[1]);
                    }
                    else {
                        pHam =1.0F;
                    }
                }
                resultHam*=pSpam;
                resultSpam*=pHam;
            }
            if(resultSpam >= resultHam){
                classify = "SPAM";
            }
            else
            {
                classify ="HAM";
            }

            String output = counter + ") " + sub + " ********** spam = " + resultSpam + "%  ham = " + resultHam +"%  classify = " + classify + "*************\n" ;
            counter++;
            finalList.add(output);
        }
        try
        {
            FileWriter writer = new FileWriter("JavaNaiveBayes-accuracy.txt");
            for(String str: finalList) {
                writer.write(str);
            }
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
