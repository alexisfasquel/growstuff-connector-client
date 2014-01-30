package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 29/01/14
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public class Footer extends JPanel{

    private final Button mApply = new Button("Configurer");

    public Footer() {
        setBackground(GrowRes.GREEN);
        add(mApply);
    }




    private class Button extends JButton implements MouseListener{


        private Font mNormalFont = new Font(getFont().getName(), Font.PLAIN, getFont().getSize() + 5);
        private Font mBoldFont = new Font(getFont().getName(), Font.BOLD, getFont().getSize() + 5);

        public Button (String content) {
            super(content);
            setFont(mBoldFont);
            addMouseListener(this);
        }




        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Dimension dimension = getSize();

            g2d.setColor(GrowRes.WHITE);
            int offset = dimension.height/2;

            g2d.fillRect(offset, 0, dimension.width-dimension.height, dimension.height);

            g2d.fillOval(0, 0, dimension.height, dimension.height);
            g2d.fillOval(dimension.width-dimension.height, 0, dimension.height, dimension.height);

            g2d.setColor(GrowRes.GREEN);

            FontMetrics metrics = g2d.getFontMetrics(getFont());

            int heightPadding= dimension.height/2 - metrics.getHeight()/2;
            System.out.println(heightPadding);
            int widthPadding = dimension.width/2 - metrics.stringWidth(getText())/2;

            g2d.drawString(getText(), widthPadding, heightPadding+10);


        }


        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            this.setFont(mBoldFont);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.setFont(mNormalFont);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            this.setFont(mNormalFont);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.setFont(mBoldFont);
        }
    }
}
