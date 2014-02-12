package ui.components;

import ui.GrowRes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ReloadButton extends JButton implements MouseListener {

    private Image mImage = GrowRes.getImage(GrowRes.RELOAD);
    private boolean mPressed = false;
    private double mAngle;
    private Animation mAnimation = null;

    public ReloadButton() {
        setPreferredSize(new Dimension(mImage.getWidth(null)/2, mImage.getHeight(null)/2));
        addMouseListener(this);
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.rotate(mAngle, getWidth() / 2, getHeight() / 2);
        if(mPressed) {
            g2.drawImage(mImage, 0, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
        } else {
            g2.drawImage(mImage, 0, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
        }
    }

    public void animate (boolean yes) {
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
                for(int i = 0; i <= 360 ; i += 4) {
                    try {
                        mAngle = Math.toRadians(i);
                        Thread.sleep(5);
                        repaint();
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
