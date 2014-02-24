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


    private static final String NETWORK_CMD = "sudo ./networks/configure.sh SSID PSK";
    private static final String SOCKET_CMD = "./socket/startSocket.sh ID";

    public static boolean configure(String ssid, String psk, String id) {

        JSch jsch=new JSch();
        try {

            Session session = jsch.getSession("pi", "192.168.0.1", 22);

            session.setUserInfo(new Infos());
            session.setPassword("growstuff");

            session.connect(20000);

            Channel channel = session.openChannel("exec");

            // Lanching the network config script
            // But also, in our case, some other daemon
            ((ChannelExec)channel).setCommand(
                    NETWORK_CMD.replace("SSID", ssid).replace("PSK", psk) + " ; " +
                    SOCKET_CMD.replace("ID", id)
            );

            channel.setInputStream(null);
            channel.connect(20000);

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
