import javax.swing.*;
import java.awt.*;

public class Square extends JLabel {

    private Color color;

    public Square(Color color) {
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
}
