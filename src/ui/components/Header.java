package ui.components;

import ui.GrowRes;
import ui.components.WhiteLabel;

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

    private WhiteLabel mText = new WhiteLabel(GrowRes.STR_INTRO_LABEL);

    public Header() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GrowRes.GREEN);

        JPanel mTextPanel = new JPanel();
        mTextPanel.setBackground(GrowRes.GREEN);
        mTextPanel.setLayout(new GridBagLayout());

        mTextPanel.add(mText);

        add(mTextPanel);
    }

    public void setOpacity(float opacity) {
        mText.setOpacity(Math.min(1, Math.max(0, opacity)));
    }

}
