package ui;

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
                if(Wifi.connect(mCurrentSSID, mBody.getPSK())) {
                    mBody.loading();
                } else {
                    mBody.failed();
                }

                Wifi.connect("aleks", "");
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
