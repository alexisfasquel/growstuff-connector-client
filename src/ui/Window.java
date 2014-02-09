package ui;

import com.jcraft.jsch.JSchException;
import tools.RPi;
import tools.Wifi;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Aleks
 * @version : 1.0
 * Fuck NYC (and Melo') :D
 *
 * Main ui.Window of the application
 */
public class Window extends JFrame {



    private Header mHeader;
    private Body mBody;
    private Footer mFooter;

    private String mCurrentSSID;

    public Window() {


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(GrowRes.GREEN);

        mCurrentSSID = Wifi.getCurrentSSID();

        mHeader = new Header();
        mBody = new Body(Wifi.getCurrentSSID());
        mFooter = new Footer();

        mFooter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String password = mBody.getPSK();

                //mBody.getPSK()

                if(Wifi.connect(mCurrentSSID, password)) {
                    mBody.loading();
                } else {
                    mBody.failed();
                }

                if (Wifi.connect("GrowStuff", "") && Wifi.checkIpAdress()) {
                    try {
                        RPi.configure(mCurrentSSID, password);
                    } catch (JSchException e1) {
                        System.out.println(e1.getMessage());
                    }
                }

            }
        });

        add(mHeader);
        add(mBody);
        add(mFooter);

        setVisible(true);
    }

    public static void main (String[] args) {
        new Window();
    }
}
