package ui;

import ui.components.Body;
import ui.components.Footer;
import ui.components.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 10/02/14
 * Time: 05:02
 * To change this template use File | Settings | File Templates.
 */
public class MainPanel extends JPanel {


    private Window.AnimationListener mListener;

    private Header mHeader;
    private Body mBody;
    private Footer mFooter;

    private Image mLogo = GrowRes.getImage(GrowRes.LOGO);

    public MainPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mHeader = new Header();
        mFooter = new Footer();
        mBody = new Body(mFooter);

        setBackground(GrowRes.GREEN);

        add(mHeader);
        add(mBody);
        add(mFooter);

    }


    public void addActionListener(AbstractAction listener) {
        mFooter.addActionListener(listener);
    }


    public void disappear(Window.AnimationListener listener) {
        new DisapearAnimation().start();
        mListener = listener;
    }

    public String getPSK() {
        return mBody.getPSK();
    }

    public String getSSID() {
        return mBody.getSSID();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(mLogo, 20, 15, mLogo.getWidth(null)/2, mLogo.getHeight(null)/2, null);
    }


    //Animating a little bit (yep, I know, that's ugly)
    private class DisapearAnimation extends Thread {

        @Override
        public void run() {
            try {

                int height = mHeader.getHeight();
                int i = 0;
                float opacity = 1;
                while ((height += 5) <= getHeight()/1.3f) {
                    mHeader.setPreferredSize(new Dimension(Short.MAX_VALUE, height));
                    mHeader.setMinimumSize(new Dimension(Short.MAX_VALUE, height));
                    mHeader.setMaximumSize(new Dimension(Short.MAX_VALUE, height));

                    mHeader.setOpacity(opacity);
                    mBody.setOpacity(opacity);
                    mFooter.setOpacity(opacity);
                    Thread.sleep(Math.abs(50 - i*2));
                    mHeader.revalidate();
                    revalidate();
                    i++;
                    opacity -= 0.08f;

                }
                mListener.onFinish();
            } catch (InterruptedException e) {}
        }
    }


}
