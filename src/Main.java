import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manpreet Gandhi on 5/21/2016.
 */
public class Main {

    public static void main(String[] args) {

        Map<String, Integer> hm = new HashMap<String, Integer>();

        BufferedReader br = null;



        try {

            String sCurrentLine;
            String s;
            br = new BufferedReader(new FileReader("C:\\Users\\Manpreet Gandhi\\Desktop\\Lab2-CS286\\spam\\0009.c05e264fbf18783099b53dbc9a9aacda"));

            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.toString().contains("Subject"))
                {
                    if(sCurrentLine.toString().contains("RE:")) {
                        s = sCurrentLine.substring(sCurrentLine.indexOf("RE:")+3);
                        System.out.println(s);
                    }
                    else
                    {
                        s = sCurrentLine.substring(sCurrentLine.indexOf("Subject:") + 8);
                        System.out.println(s);
                    }

                    String[] str = s.split(" ");
                    System.out.println("inside subject");
                    for (String a : str) {
                        if(!a.contains(" ")) {
                            if (hm.containsKey(a)) {
                                System.out.println("inside contains");
                                hm.put(a, hm.get(a) + 1);
                            }
                            else
                            {
                                hm.put(a, 1);
                            }
                        }
                        System.out.println(a);
                    }
                }
                else
                {
                    continue;
                }
            }

            System.out.println(" otsside loop");
            System.out.println(" length of the hashmap = "+ hm.size());
            for ( String l : hm.keySet()) {
                System.out.println(l + " outside loop");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
