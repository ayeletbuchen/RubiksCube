package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class ShuffleBar extends JPanel {

    private Cube cube;
    private Solver solver;
    private JButton shuffleButton;
    private JButton resetButton;
    private JButton solveButton;
    private String teachMeStr;
    private String solveAloneStr;
    private DirectionLabel directionLabel;

    public ShuffleBar(Cube cube, Solver solver, DirectionLabel directionLabel) {
        this.cube = cube;
        this.solver = solver;
        this.directionLabel = directionLabel;
        teachMeStr = "Teach me";
        solveAloneStr = "Solve alone";
        setLayout(new FlowLayout());
        addResetButton();
        addShuffleButton();
        addSolveButton();
    }

    private void addResetButton() {
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            cube.reset();
            reset();
        });
        add(resetButton);
    }

    private void addShuffleButton() {
        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            cube.shuffle();
            reset();
        });
        add(shuffleButton);
    }

    private void addSolveButton() {
        solveButton = new JButton(teachMeStr);
        solveButton.addActionListener(e -> {
            if (solveButton.getText().equals(teachMeStr)) {
                directionLabel.setVisible(true);
                solveButton.setText(solveAloneStr);
                if (!solver.alreadySolved()) {
                    solver.solve();
                }
            } else {
                directionLabel.setVisible(false);
                solveButton.setText(teachMeStr);
            }
        });
        add(solveButton);
    }

    private void reset() {
        directionLabel.setVisible(false);
        solveButton.setText(teachMeStr);
        solver.clear();
    }
}
