package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
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
    JTextComponent mPSK = new InputPwd(20);

    public Body (String network) {
        setBackground(GrowRes.WHITE);
        mSSID.setForeground(GrowRes.GREEN);

        if(network == null) {
            mSSID.setText("Not connected!");
        } else {
            mSSID.setText(network);
        }

        mPSK.setCaret(new GreenCaret());

        add(mSSID);
        add(mPSK);

        requestFocus();

    }

    public String getPSK() {
        return mPSK.getText();
    }

    public void loading() {
        mPSK = new Input(20);
        repaint();
        mSSID.setText("Twitter username ?");
    }

    public void failed() {
        remove(mPSK);
        repaint();
        mSSID.setText("FAILED!");

    }


    private class InputPwd extends JPasswordField {
        public InputPwd (int size) {
            super(size);
            setForeground(GrowRes.GREEN);
        }

        @Override
        public void setBorder(Border border) {}

    }

    private class Input extends JTextField {
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

