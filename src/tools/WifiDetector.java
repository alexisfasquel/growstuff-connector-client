package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 28/01/14
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class WifiDetector {


    public void WifiDetector() {

    }


    public String getCurrentSSID() {
        Process p = null;
        StringBuffer output = new StringBuffer();
        String line = "";
        try {
            String[] cmd = {
                    "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -I",
                    "grep SSID",
                    "tail -n 1"
            };
            p = Runtime.getRuntime().exec("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -I");

            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = reader.readLine())!= null) {
                line = line .replaceAll(" ", "");
                if(line.startsWith("SSID")) {
                    output.append(line.replace("SSID:", ""));
                }
            }
             if(output.toString().equals("")) {
                 return null;
             }
            return output.toString();
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

}
