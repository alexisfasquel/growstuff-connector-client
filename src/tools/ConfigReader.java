package tools;

import java.io.*;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 18/02/14
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class ConfigReader {

    public static String getPlantId(File configFile) {

        try {
            FileReader fileReader = new FileReader(configFile);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                if(!line.startsWith("#")) {
                     if(line.startsWith("ID")) {
                         return line.split("=")[1];
                     }
                }
            }
        } catch (IOException e) {}
        return null;
    }
}
