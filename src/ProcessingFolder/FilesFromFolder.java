package ProcessingFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manpreet Gandhi on 5/22/2016.
 */
public class FilesFromFolder {

//If this pathname does not denote a directory, then listFiles() returns null.
        public ArrayList<String> fetchFileName(String s){
            File[] files = new File(s).listFiles();
            if(files == null)
            {
                System.out.println(s + " does not have any files in it");
            }
            ArrayList<String> results = new ArrayList<String>();
            for (File file : files) {
                if (file.isFile()) {
//                    System.out.println(file.getName());
                    results.add(file.getName());
                }
            }
            return results;
        }
}
