import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 28/01/14
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class Header extends JPanel {

    private JLabel mLogo = new JLabel(GrowRes.LOGO);

    public Header() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(GrowRes.GREEN);

        add(mLogo);

    }
}
