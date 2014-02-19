package ui;

import tools.ConfigReader;
import tools.RPi;
import tools.Wifi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * @author Aleks
 * @version : 1.0
 * Fuck NYC (and Melo') and OKC ! :D
 *
 * Main ui.Window of the application
 */
public class Window extends JFrame {

    private MainPanel mMainPanel;
    private LoadingPanel mLoadingPanel;

    private boolean mSucces = false;

    public interface AnimationListener {
        public void onFinish();
    }


    public Window() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 520);
        setMinimumSize(new Dimension(850, 520));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(GrowRes.GREEN);

        setLocationRelativeTo(null);


        initConfiguration();


        setContentPane(new DropPanel(new DropPanel.DropListener() {
            @Override
            public void onDroped(File droppedfile) {
                ConfigReader.getPlantId(droppedfile);
                setContentPane(mMainPanel);
                revalidate();
            }
        }));
        setVisible(true);
    }

    private void initConfiguration() {
        mLoadingPanel = new LoadingPanel();
        mLoadingPanel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mLoadingPanel.disappear(new AnimationListener() {
                    @Override
                    public void onFinish() {
                        if(mSucces) {
                            System.exit(0);
                        } else {
                            initConfiguration();
                        }
                    }
                });
            }
        });

        mMainPanel = new MainPanel();
        mMainPanel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mMainPanel.disappear(new AnimationListener() {
                    @Override
                    public void onFinish() {
                        configure();
                    }
                });
            }
        });
    }

    private void configure() {
        setContentPane(mLoadingPanel);
        revalidate();
        mLoadingPanel.appear();
        new Connector().execute();
    }




    //Animating a little bit (yep, I know, that's ugly)
    private class Connector extends SwingWorker<Boolean, Object> {
        @Override
        public Boolean doInBackground() {
            String ssid = mMainPanel.getSSID().replace(":", " ").trim();
            String password = mMainPanel.getPSK();
            if(Wifi.connect(ssid, password)) {
                if (Wifi.connect("GrowStuff", "") && Wifi.checkIpAdress()) {
                    if(RPi.configure(ssid, password)) {
                        Wifi.connect(ssid, password);
                        return true;
                    }
                }
            }
            Wifi.connect(ssid, password);
            return false;
        }

        @Override
        protected void done() {
            try {
                mSucces = get();
            } catch (InterruptedException | ExecutionException e) {}
            mLoadingPanel.stop(mSucces);
        }
    }




    public static void main (String[] args) {
        //Registering the fonts so we can use them in HTML
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GrowRes.getFont(20, false));
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GrowRes.getFont(20, true));

        new Window();
    }
}
