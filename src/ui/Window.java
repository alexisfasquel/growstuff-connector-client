package ui;

import javax.swing.*;

/**
 * @author Aleks
 * @version : 1.0
 * Fuck NYC (and Melo') :D
 *
 * Main ui.Window of the application
 */
public class Window extends JFrame {



    private Header mHeader = new Header();
    private Body mBody = new Body();
    private Footer mFooter = new Footer();

    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(GrowRes.GREEN);

        add(mHeader);
        add(mBody);
        add(mFooter);

        setVisible(true);
    }

    public static void main (String[] args) {
        new Window();
    }
}
