package ui.components;

import ui.GrowRes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 29/01/14
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public class Footer extends JPanel {

    private final Button mApply = new Button(GrowRes.STR_CONFIGURE_BUTTON);

    private Image mArrowIcoWhite;
    private Image mArrowIcoGreen;

    private boolean mfilled = false;

    public Footer() {

        setBackground(GrowRes.GREEN);
        setLayout(new GridBagLayout());

        //Setting up the button centered in the second column
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.weightx = 2.0;
        gbc.gridx = 2;

        add(mApply, gbc);

        gbc.gridx = 1;
        //Adding a panel in the first column to fill up the space
        JPanel p = new JPanel();
        p.setBackground(GrowRes.GREEN);
        add(p, gbc);

        mArrowIcoWhite = GrowRes.getImage(GrowRes.ARROW_WHITE);
        mArrowIcoGreen = GrowRes.getImage(GrowRes.ARROW_GREEN);

    }

    @Override
    public void setEnabled(boolean yesorno) {
        mApply.setEnabled(yesorno);
    }

    public void addActionListener(ActionListener action) {
        mApply.addActionListener(action);
        mApply.registerKeyboardAction(action, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), WHEN_IN_FOCUSED_WINDOW);
    }

    public void setOpacity(float opacity) {
        mApply.setOpacity(Math.min(1, Math.max(0, opacity)));
    }

    public void setText(String text) {
        mApply.setText(text);
    }


    private class Button extends JButton implements MouseListener{

        private float mOpacity = 1;

        public Button (String content) {
            super(content);

            setFont(GrowRes.getFont(20, true));
            setMargin(new Insets(5, 10, 5, 10));
            addMouseListener(this);
        }

        public void setOpacity(float opacity) {
            mOpacity = opacity;
        }


        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mOpacity);
            g2d.setComposite(alphaComposite);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Dimension dimension = getSize();



            g2d.setColor(GrowRes.WHITE);
            g2d.setStroke(new BasicStroke(2));

            if(mfilled) {
                g.fillRoundRect(2, 2, dimension.width-4, dimension.height-4, dimension.height, dimension.height);
                g2d.setColor(GrowRes.GREEN);
                g2d.drawImage(mArrowIcoGreen, dimension.width - 30, dimension.height/2 - 8, mArrowIcoWhite.getWidth(null)/2, mArrowIcoWhite.getHeight(null)/2, null);
            } else {
                g.drawRoundRect(2, 2, dimension.width - 4, dimension.height - 4, dimension.height, dimension.height);
                g2d.setColor(GrowRes.WHITE);
                g2d.drawImage(mArrowIcoWhite, dimension.width - 30, dimension.height/2 - 8, mArrowIcoWhite.getWidth(null)/2, mArrowIcoWhite.getHeight(null)/2, null);
            }
            FontMetrics metrics = g2d.getFontMetrics(getFont());

            int heightPadding= metrics.getHeight() + 4;
            int widthPadding = dimension.width/2 - metrics.stringWidth(getText())/2 - 7;

            g2d.drawString(getText(), widthPadding, heightPadding);


        }


        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            mfilled = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            mfilled = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

    }
}
