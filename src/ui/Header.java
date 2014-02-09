package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 28/01/14
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class Header extends JPanel {

    private Logo mLogo = new Logo();
    private WhiteLabel mText = new WhiteLabel(GrowRes.STR_INTRO_LABEL, SwingConstants.CENTER);

    public Header() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GrowRes.GREEN);

        JPanel mLogoPanel = new JPanel();
        mLogoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mLogoPanel.add(mLogo);
        mLogoPanel.setBackground(GrowRes.GREEN);

        mLogoPanel.setMinimumSize(new Dimension(Short.MAX_VALUE, 50));
        mLogoPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));


        JPanel mTextPanel = new JPanel();
        mTextPanel.setBackground(GrowRes.GREEN);
        mTextPanel.setLayout(new GridBagLayout());

        mText.setForeground(GrowRes.WHITE);
        mText.setBackground(GrowRes.GREEN);

        mTextPanel.add(mText);


        add(mLogoPanel);
        add(mTextPanel);
    }

    public void setOpacity(float opacity) {
        mText.setOpacity(Math.min(1, Math.max(0, opacity)));
    }

    private class Logo extends JButton {

        Image mImage = GrowRes.getImage(GrowRes.LOGO);

        public Logo() {
            setPreferredSize(new Dimension(mImage.getWidth(null)/2, mImage.getHeight(null)/2));
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(mImage, 0, 0, mImage.getWidth(null)/2, mImage.getHeight(null)/2, null);
        }
    }


    private class WhiteLabel extends JLabel {
        private float mOpacity = 1f;

        public WhiteLabel(String mess, int constant) {
            super(mess, constant);
        }

        public void setOpacity(float opacity) {
            mOpacity = opacity;
        }

        @Override
        protected void paintComponent( Graphics g )
        {
            Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mOpacity);
            Graphics2D g2d = (Graphics2D)g.create();
            g2d.setComposite( alphaComposite );
            super.paintComponent( g2d );
        }

    }

}
