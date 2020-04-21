package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private Cube cube;
    private Solver solver;
    private JButton shuffleButton;

    public ControlPanel(Cube cube, Solver solver) {
        this.cube = cube;
        this.solver = solver;
        setLayout(new BorderLayout());
        add(new RotationsPanel(cube, solver), BorderLayout.CENTER);
        addShuffleButton();
    }

    private void addShuffleButton() {
        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            cube.shuffle();
            solver.solve();
        });
        add(shuffleButton, BorderLayout.SOUTH);
    }
}
