package ui.components;

import tools.Wifi;
import ui.GrowRes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 29/01/14
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class Body extends JPanel {

    private float mOpacity = 1;

    private JPanel mContent = new JPanel();

    private JLabel mSSID = new JLabel();
    private JTextComponent mPSK = new InputPwd(20);
    private ReloadButton mReloadButton = new ReloadButton();

    public Body () {

        setLayout(new GridBagLayout());
        setBackground(GrowRes.WHITE);


        mSSID.setForeground(GrowRes.GREEN);
        mSSID.setFont(GrowRes.getFont(22, true));
        mSSID.setBackground(Color.black);
        mPSK.setCaret(new GreenCaret());

        mPSK.setFont(GrowRes.getFont(25, true));

        mContent.setBackground(GrowRes.WHITE);

        mContent.add(mSSID);
        mContent.add(mPSK);
        mContent.add(mReloadButton);
        mContent.setOpaque(false);

        add(mContent);

        setSSID();

        mReloadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSSID();
                mReloadButton.animate(false);
            }
        });

        requestFocus();

    }

    public void setSSID() {
        String network;
        if((network = Wifi.getCurrentSSID()) != null) {
            mSSID.setText(network + " : ");
            mPSK.setEnabled(true);
        } else {
            mPSK.setEnabled(false);
            mSSID.setText("No connection");
        }
    }

    public String getSSID() {
        return mSSID.getText();
    }

    public String getPSK() {
        return mPSK.getText();
    }

    public void setOpacity(float opacity) {
        mOpacity = Math.min(1, Math.max(0, opacity));
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mOpacity);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setComposite( alphaComposite );
        super.paintComponent(g2);
    }


    private class InputPwd extends JPasswordField {

        public InputPwd (int size) {
            super(size);
            setForeground(GrowRes.GREEN);
            setOpaque(false);
        }

        @Override
        public void setEchoChar(char c) {
            super.setEchoChar('*');
        }

        @Override
        public void setBorder(Border border) {}

        @Override
        protected void paintComponent( Graphics g )
        {
            Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mOpacity);
            Graphics2D g2d = (Graphics2D)g.create();
            g2d.setComposite( alphaComposite );
            super.paintComponent( g2d );
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Short.MAX_VALUE, 100);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(Short.MAX_VALUE, 100);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Short.MAX_VALUE, 100);
    }



}

