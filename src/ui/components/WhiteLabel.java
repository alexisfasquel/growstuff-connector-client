package ui.components;

import ui.GrowRes;

import javax.swing.*;
import java.awt.*;

public class WhiteLabel extends JLabel {
    private float mOpacity = 1f;

    public WhiteLabel(String mess) {
        super(mess, SwingConstants.CENTER);
        setForeground(GrowRes.WHITE);
        setBackground(GrowRes.GREEN);
    }

    public void setOpacity(float opacity) {
        mOpacity = Math.min(1, Math.max(0, opacity));
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mOpacity);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(alphaComposite);
        super.paintComponent(g2d);
    }

    @Override
    public void setText(final String text) {
        super.setText(text);
    }



}