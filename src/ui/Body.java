package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 29/01/14
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class Body extends JPanel {


    JLabel mSSID = new JLabel();
    Input mPSK = new Input(20);


    WifiDetector wifiDetector = new WifiDetector();

    public Body () {
        setBackground(GrowRes.WHITE);
        mSSID.setForeground(GrowRes.GREEN);

        Component c = getTopLevelAncestor();

        if(c == null)
            System.out.println("ARG");


        String ssid = wifiDetector.getCurrentSSID();
        if(ssid == null) {
            mSSID.setText("Not connected!");
        } else {
            mSSID.setText(ssid);
        }

        mPSK.setCaret(new GreenCaret());


        add(mSSID);
        add(mPSK);

        requestFocus();

    }


    private class Input extends JPasswordField {
        public Input (int size) {
            super(size);
            setForeground(GrowRes.GREEN);
        }

        @Override
        public void setBorder(Border border) {}

    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Short.MAX_VALUE, 50);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(Short.MAX_VALUE, 50);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Short.MAX_VALUE, 50);
    }
}

