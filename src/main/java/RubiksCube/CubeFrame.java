package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class CubeFrame extends JFrame {

    public CubeFrame() {
        setTitle("Rubik's Cube");
        setSize(FrameValues.FRAME_WIDTH, FrameValues.FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel root = new JPanel(new BorderLayout());
        Cube cube = new Cube();
        ControlPanel controlPanel = new ControlPanel(cube);
        root.add(cube, BorderLayout.CENTER);
        root.add(controlPanel, BorderLayout.SOUTH);
        setContentPane(root);
    }
}