package ui;

import com.jcraft.jsch.JSchException;
import tools.RPi;
import tools.Wifi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

/**
 * @author Aleks
 * @version : 1.0
 * Fuck NYC (and Melo') and OKC ! :D
 *
 * Main ui.Window of the application
 */
public class Window extends JFrame {



    private Header mHeader;
    private Body mBody;
    private Footer mFooter;


    private LoadingPanel  mLoadingPanel = new LoadingPanel();

    private String mCurrentSSID;

    public Window() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(GrowRes.GREEN);

        mCurrentSSID = Wifi.getCurrentSSID();

        mHeader = new Header();
        mBody = new Body(Wifi.getCurrentSSID());
        mFooter = new Footer();

        mFooter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisapearAnimation().execute();
                new Connector().execute();

            }
        });

        mLoadingPanel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mLoadingPanel.disappear();
            }
        });

        add(mHeader);
        add(mBody);
        add(mFooter);

        setVisible(true);

    }


    //Animating a little bit (yep, I know, that's ugly)
    private class Connector extends SwingWorker<Boolean, Object> {
        @Override
        public Boolean doInBackground() {
            String password = mBody.getPSK();

            if(Wifi.connect(mCurrentSSID, password)) {
                if (Wifi.connect("GrowStuff", "") && Wifi.checkIpAdress()) {
                    try {
                        RPi.configure(mCurrentSSID, password);
                    } catch (JSchException e1) {
                        System.out.println(e1.getMessage());
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        }

        @Override
        protected void done() {
            try {
                mLoadingPanel.stop(get());
            } catch (InterruptedException | ExecutionException e) {}
        }
    }


    //Animating a little bit (yep, I know, that's ugly)
    private class DisapearAnimation extends SwingWorker<Void, Object> {
        @Override
        public Void doInBackground() {
            try {

                int height = mHeader.getHeight();
                int i = 0;
                float opacity = 1;
                while ((height += 6) <= getHeight()) {
                    mHeader.setPreferredSize(new Dimension(Short.MAX_VALUE, height));
                    mHeader.setMinimumSize(new Dimension(Short.MAX_VALUE, height));
                    mHeader.setMaximumSize(new Dimension(Short.MAX_VALUE, height));

                    mHeader.setOpacity(opacity);
                    mBody.setOpacity(opacity);
                    mFooter.setOpacity(opacity);
                    Thread.sleep(Math.abs(40 - i*2));
                    mHeader.revalidate();
                    revalidate();
                    i++;
                    opacity -= 0.08f;

                }
            } catch (InterruptedException e) {

            }
            return null;
        }

        @Override
        protected void done() {
            remove(mHeader);
            remove(mBody);
            remove(mFooter);

            revalidate();

            add(mLoadingPanel);
            revalidate();
            mLoadingPanel.appear();
        }
    }




    public static void main (String[] args) {
        //Registering the fonts so we can use them in HTML
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GrowRes.getFont(20, false));
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GrowRes.getFont(20, true));

        new Window();
    }
}
