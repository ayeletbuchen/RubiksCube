package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class Square extends JLabel {

    private Color color;
    private final Color ORIGINAL_COLOR;

    public Square(Color color) {
        ORIGINAL_COLOR = color;
        setOpaque(true);
        setColor(color);
        setSize(50, 50);
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public Color getColor() {
        return color;
    }

    public Color getORIGINAL_COLOR() {
        return ORIGINAL_COLOR;
    }
}
