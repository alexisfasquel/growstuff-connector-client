package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

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
    private ReloadButton mButton = new ReloadButton();

    public Body (String network) {

        setLayout(new GridBagLayout());
        setBackground(GrowRes.WHITE);


        mSSID.setForeground(GrowRes.GREEN);
        mSSID.setFont(GrowRes.getFont(22, true));
        mSSID.setBackground(Color.black);
        mPSK.setCaret(new GreenCaret());

        mPSK.setFont(GrowRes.getFont(25, true));


        if(network == null) {
            mSSID.setText("Not connected!");
        } else {
            mSSID.setText(network + " :  ");
        }

        mContent.setBackground(GrowRes.WHITE);

        mContent.add(mSSID);
        mContent.add(mPSK);
        mContent.add(mButton);
        mContent.setOpaque(false);

        add(mContent);

        requestFocus();

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


    public String getPSK() {
        return mPSK.getText();
    }


    private class ReloadButton extends JButton implements MouseListener {

        private Image mImage = GrowRes.getImage(GrowRes.RELOAD);
        private boolean mPressed = false;
        private double mAngle;
        private Animation mAnimation = null;

        public ReloadButton() {
            setPreferredSize(new Dimension(mImage.getWidth(null)/2, mImage.getHeight(null)/2));
            addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.rotate(mAngle, getWidth() / 2, getHeight() / 2);
            if(mPressed) {
                g2.drawImage(mImage, 5, 5, mImage.getWidth(null)/3, mImage.getHeight(null)/3, null);
            } else {
                g2.drawImage(mImage, 0, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
            }
        }

        private void animate (boolean yes) {
            if(yes && mAnimation == null) {
                removeMouseListener(this);
                mAnimation = new Animation();
                mAnimation.start();
            } else {
                mAnimation.finish();
                mAnimation = null;
                addMouseListener(this);
            }

        }

        private class Animation extends Thread {

            private boolean mAnimate = true;
            @Override
            public void run() {

                while (mAnimate) {
                    for(int i = 0; i <= 360 ; i += 2) {
                        try {
                            mAngle = Math.toRadians(i);
                            Thread.sleep(20);
                            mButton.repaint();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                mPressed = false;
            }

            public void finish() {
                mAnimate = false;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            mPressed = true;
            animate(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
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

    private class Input extends JTextField {
        private float mOpacity = 1;

        public Input (int size) {
            super(size);
            setForeground(GrowRes.GREEN);
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

