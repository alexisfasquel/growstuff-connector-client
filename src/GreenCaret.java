/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly 
*/
// GreenCaret.java
//Another (fancier) custom caret class.
//

import java.awt.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

public class GreenCaret extends DefaultCaret {

    public GreenCaret() {
        setBlinkRate(750);
    }

    @Override
    public void paint(Graphics g) {
        if(!isVisible())
            return;
        Graphics2D g2=(Graphics2D)g;
        try
        {
            JTextComponent component = getComponent();
            // Convert model to view, so that we get
            // a rectangle whose x and y co ordinate
            // is the x,y of the caret and width and height
            // is the width and height of the caret
            Rectangle r= component.modelToView(component.getCaretPosition());

            // Set some gradient paint for the caret
            // The gradient starting should be the x,y of the caret (which changes with
            // caret position) and the starting of the second color should be
            // x,r.y+height. r.y+r.height is used so that it will act like a linear
            // gradient paint. The gradient comes with red at top, gray at bottom
            g2.setColor(GrowRes.GREEN);

            // Fill the shape with the gradient
            // The shape starting should be r.x,r.y (pos of caret)
            // and width should be 2 and height is height of the caret (which
            // depends upon the font height
            g2.fillRect(r.x, r.y, 5, r.height);

        }catch(Exception e){}

    }
}
