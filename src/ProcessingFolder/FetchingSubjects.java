package ProcessingFolder; /**
 * Created by Manpreet Gandhi on 5/22/2016.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchingSubjects {

//    public static void main(String[] args) {

//        Map<String, Integer> hm = new HashMap<String, Integer>();


//        String Path = "C:\\Users\\Manpreet Gandhi\\Desktop\\Lab2-CS286\\spam";

        public ArrayList<String> returnSubjects(String Path){
            ArrayList<String> Subjects = new ArrayList<String>();
            BufferedReader br = null;
            FilesFromFolder FFF = new FilesFromFolder();
            ArrayList<String> fileNames = FFF.fetchFileName(Path);
            for(String fileN: fileNames) {

                try {
                    String sCurrentLine;
                    String s;
                    br = new BufferedReader(new FileReader(Path+"\\"+fileN));

                    while ((sCurrentLine = br.readLine()) != null) {
                        if (sCurrentLine.toString().contains("Subject")) {
                            if (sCurrentLine.toString().contains("RE:")) {
                                s = sCurrentLine.substring(sCurrentLine.indexOf("RE:") + 3);
                            } else {
                                s = sCurrentLine.substring(sCurrentLine.indexOf("Subject:") + 8);
                            }
                            Subjects.add(s);
                        }
                        else {
                            continue;
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        if (br != null) br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            System.out.println(Path + "\n size  is " +  Subjects.size());
        return Subjects;
    }

    public HashMap<String, Integer> getSubjectMap(List<String> str) {
        HashMap<String, Integer> SubjectMap = new HashMap<>();
        for(String s : str) {
            String[] words = s.split(" ");
            for (String a : words) {
                if (!a.contains(" ")) {
                    if (SubjectMap.containsKey(a.trim()) && SubjectMap.get(a)!=null)
                    {
                        SubjectMap.put(a.trim(), SubjectMap.get(a.trim()) + 1);
                    }
                    else {
                        SubjectMap.put(a.trim(), 1);
                    }
                }
            }
        }
        return SubjectMap;
    }
}

