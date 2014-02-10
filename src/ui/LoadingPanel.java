package ui;

import ui.components.WhiteLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 09/02/14
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */
public class LoadingPanel extends JPanel {


    private LoadingIcon mLoadingIcon = new LoadingIcon();
    private WhiteLabel mWhiteLabel = new WhiteLabel(GrowRes.STR_WAITING_LABEL);
    private Footer mFooter = new Footer();

    private float mOpacity = 1;

    public LoadingPanel() {
        setBackground(GrowRes.GREEN);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 1;
        gbc.gridy = 0;

        add(mLoadingIcon, gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
;
        add(mWhiteLabel, gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 2;

        add(mFooter, gbc);

        new Thread(mLoadingIcon).start();

    }

    public void addActionListener(AbstractAction action) {
        mFooter.addActionListener(action);
    }

    public void appear() {
        new AppearAnimation().start();
    }

    public void disappear() {
        new DisappearAnimation().start();
    }

    public void stop(boolean succes) {
        mLoadingIcon.stop();
        if(succes) {
            mWhiteLabel.setText("Succes");
        } else {
            mWhiteLabel.setText("Failed");
        }
        new FooterAppearAnimation().start();
    }




    public class LoadingIcon extends JPanel implements Runnable{


        private boolean mContinue = true;
        private Image mImage = GrowRes.getImage(GrowRes.LOADING_1);

        public LoadingIcon() {
            setBackground(GrowRes.GREEN);
            setPreferredSize(new Dimension(mImage.getWidth(null) / 2, mImage.getHeight(null) / 2));
        }

        public void stop() {
            mContinue = false;
        }

        @Override
        public void run() {
            while(mContinue) {
                try {
                    Thread.sleep(1000);
                    mImage = GrowRes.getImage(GrowRes.LOADING_2);
                    repaint();
                    Thread.sleep(500);
                    mImage = GrowRes.getImage(GrowRes.LOADING_3);
                    repaint();
                    Thread.sleep(1000);
                    mImage = GrowRes.getImage(GrowRes.LOADING_1);
                    repaint();
                } catch (InterruptedException e) {}
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mOpacity);
            g2.setComposite(alphaComposite);

            g2.drawImage(mImage, getWidth()/2-mImage.getWidth(null)/4, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
        }
    }



    //Animating a little bit (yep, I know, that's ugly)
    public class DisappearAnimation extends Thread {
        @Override
        public void run() {
            float opacity = 1;
            while(opacity >= 0) {
                mOpacity = opacity;
                mWhiteLabel.setOpacity(opacity);
                mFooter.setOpacity(opacity);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {}
                opacity -= 0.05f;
                repaint();
            }
            mOpacity = 0;
            mWhiteLabel.setOpacity(0);
            mFooter.setOpacity(0);
        }
    }



    //Animating a little bit (yep, I know, that's ugly)
    public class AppearAnimation extends Thread {
        @Override
        public void run() {
            float opacity = 0;
            while(opacity <= 1) {
                mOpacity = opacity;
                mWhiteLabel.setOpacity(opacity);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {}
                opacity += 0.05f;
                repaint();
            }
            mOpacity = 1;
            mWhiteLabel.setOpacity(1);
        }
    }

    //Animating a little bit (yep, I know, that's ugly)
    public class FooterAppearAnimation extends Thread {
        @Override
        public void run() {
            float opacity = 0;
            while(opacity <= 1) {
                mFooter.setOpacity(opacity);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {}
                opacity += 0.05f;
                repaint();
            }
            mFooter.setOpacity(1);
        }
    }


}
