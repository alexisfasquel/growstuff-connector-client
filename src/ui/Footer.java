package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    Image mArrowIcoWhite;
    Image mArrowIcoGreen;

    boolean mfilled = false;

    public Footer() {

        setBackground(GrowRes.GREEN);
        setLayout(new GridBagLayout());
        try {
            mArrowIcoWhite = ImageIO.read(new File("res/arrow_ico_white.png"));
            mArrowIcoGreen = ImageIO.read(new File("res/arrow_ico_green.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(mApply);


        add(p);

    }

    public void addActionListener(AbstractAction action) {
        mApply.addActionListener(action);
    }


    public void setOpacity(float opacity) {
        mApply.setOpacity(Math.min(1, Math.max(0, opacity)));
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
