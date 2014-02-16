package tools;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 03/02/14
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class RPi {


    public static boolean configure(String SSID, String PSK) {
        JSch jsch=new JSch();


        try {


            Session session = jsch.getSession("pi", "192.168.0.1", 22);
            Thread.sleep(10000);

            session.setUserInfo(new Infos());
            session.setPassword("growstuff");

            session.connect();



            Channel channel = session.openChannel("exec");

            ((ChannelExec)channel).setCommand("sudo ./networks/configure.sh " + SSID + " " + PSK + " &");

            channel.setInputStream(null);

            channel.connect();
            Thread.sleep(1000);

            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    private static class Infos implements UserInfo {

        @Override
        public String getPassphrase() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getPassword() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean promptPassword(String s) {
           return true;
        }

        @Override
        public boolean promptPassphrase(String s) {
            return true;
        }

        @Override
        public boolean promptYesNo(String s) {
            return true;
        }

        @Override
        public void showMessage(String s) {}
    }

}
