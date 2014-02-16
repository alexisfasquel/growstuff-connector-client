package ui.components;

import ui.GrowRes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ReloadButton extends JButton implements ActionListener {

    private Image mImage = GrowRes.getImage(GrowRes.RELOAD);
    private double mAngle = 0;
    private Animation mAnimation = null;

    private boolean mAnimate = false;

    public ReloadButton() {
        setPreferredSize(new Dimension(mImage.getWidth(null)/2, mImage.getHeight(null)/2));
        addActionListener(this);
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.rotate(mAngle, getWidth() / 2, getHeight() / 2);
        g2.drawImage(mImage, 0, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
    }

    public void animate() {
        if(!mAnimate) {
            mAnimation = new Animation();
            mAnimation.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!mAnimate) {
            animate();
        }
    }

    private class Animation extends Thread {

        @Override
        public void run() {
            mAnimate = true;
            while (mAnimate) {
                for(int i = 0; i <= 360 ; i += 4) {
                    try {
                        mAngle = Math.toRadians(i);
                        Thread.sleep(5);
                        repaint();
                    } catch (InterruptedException e) {
                    }
                }
                mAnimate = false;
            }
        }
    }

}
