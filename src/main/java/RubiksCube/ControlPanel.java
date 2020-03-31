package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private Cube cube;
    private JButton shuffleButton;

    public ControlPanel(Cube cube) {
        this.cube = cube;
        setLayout(new BorderLayout());
        add(new RotationsPanel(cube), BorderLayout.CENTER);
        addShuffleButton();
    }

    private void addShuffleButton() {
        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> cube.shuffle());
        add(shuffleButton, BorderLayout.SOUTH);
    }
}
