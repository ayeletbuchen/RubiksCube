package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class DirectionLabel extends JLabel {

    public DirectionLabel() {
        setFont(new Font("Calibri", Font.PLAIN, 24));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setText("");
    }
}
