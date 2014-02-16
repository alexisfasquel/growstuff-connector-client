package ui.components;

import tools.Wifi;
import ui.GrowRes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
    private InputPwd mPSK = new InputPwd(20);
    private ReloadButton mReloadButton = new ReloadButton();
    private HideShowButton mHideShowButton = new HideShowButton();

    private Footer mFooter;

    public Body (Footer footer) {

        mFooter = footer;

        setLayout(new GridBagLayout());
        setBackground(GrowRes.WHITE);


        mSSID.setForeground(GrowRes.GREEN);
        mSSID.setFont(GrowRes.getFont(22, true));
        mSSID.setBackground(Color.black);
        mPSK.setCaret(new GreenCaret());

        mPSK.setFont(GrowRes.getFont(22, true));

        mContent.setBackground(GrowRes.WHITE);
        mContent.setLayout(new FlowLayout(FlowLayout.LEADING, 25, getHeight()));

        mContent.add(mReloadButton);
        mContent.add(mSSID);
        mContent.add(mPSK);
        mContent.add(mHideShowButton);
        mContent.setOpaque(false);

        add(mContent);

        setSSID();

        mReloadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSSID();
            }
        });

        requestFocus();

    }

    public void setSSID() {
        String network;
        if((network = Wifi.getCurrentSSID()) != null) {
            mSSID.setText(network + " : ");
            mPSK.setEnabled(true);
            mFooter.setEnabled(true);
        } else {
            mPSK.setEnabled(false);
            mFooter.setEnabled(false);
            mSSID.setText("No connection");
        }
        mPSK.setColumns(Math.max(5, 25 - mSSID.getText().length() / 2));
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


    private class InputPwd extends JPasswordField implements FocusListener{

        public InputPwd (int size) {
            super(size);
            setForeground(GrowRes.GREEN);
            setOpaque(false);
            setEchoChar('•');
            addFocusListener(this);
            focusLost(null);
        }

        public void showChars() {
            mPSK.setEchoChar((char) 0);
        }
        public void hideChars() {
            mPSK.setEchoChar('•');
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

        @Override
        public void focusGained(FocusEvent e) {
            if(getForeground() == GrowRes.GREY) {
                setText("");
                setForeground(GrowRes.GREEN);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if(getText().equals("")) {
                setText("VeryNiceWPA2.0PasswordRight?");
                setForeground(GrowRes.GREY);
            }

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


    private class HideShowButton extends JButton implements ActionListener{

        private boolean mHidden = true;
        private Image mShowImage = GrowRes.getImage(GrowRes.SHOW);
        private Image mHideImage = GrowRes.getImage(GrowRes.HIDE);


        public HideShowButton() {
            setPreferredSize(new Dimension(mShowImage.getWidth(null)/2, mShowImage.getHeight(null)/2));
            addActionListener(this);
            setOpaque(false);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if(mHidden) {
                g2.drawImage(mShowImage, 0, 0, mShowImage.getWidth(null)/2, mShowImage.getHeight(null)/2, null);
            } else {
                g2.drawImage(mHideImage, 0, 0, mHideImage.getWidth(null)/2, mHideImage.getHeight(null)/2, null);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(mHidden) {
                mHidden = false;
                mPSK.showChars();
                repaint();
            } else {
                mHidden = true;
                mPSK.hideChars();
                repaint();
            }

        }
    }



}

