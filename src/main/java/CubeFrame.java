import javax.swing.*;
import java.awt.*;

public class CubeFrame extends JFrame {

    private final int WIDTH = 750;
    private final int HEIGHT = 1334;

    public CubeFrame() {
        setTitle("Rubik's Cube");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        Cube cube = new Cube();
        root.add(cube, BorderLayout.CENTER);
        setContentPane(root);
    }
}
