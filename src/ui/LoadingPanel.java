package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 09/02/14
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */
public class LoadingPanel extends JPanel{


    private LoadingIcon mLoadingIcon = new LoadingIcon();

    public LoadingPanel() {
        setBackground(GrowRes.GREEN);
        setLayout(new GridBagLayout());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(GrowRes.GREEN);
        p.add(mLoadingIcon);

        JLabel label = new JLabel(GrowRes.STR_WAITING_LABEL);
        label.setFont(GrowRes.getFont(20, false));
        label.setForeground(GrowRes.WHITE);

        p.add(label);

        new Thread(mLoadingIcon).start();
        add(p);
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
            g2.drawImage(mImage, getWidth()/2-mImage.getWidth(null)/4, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
        }
    }

}
