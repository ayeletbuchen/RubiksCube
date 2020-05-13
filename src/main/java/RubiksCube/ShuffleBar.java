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

    public ShuffleBar(Cube cube, Solver solver) {
        this.cube = cube;
        this.solver = solver;
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
            resetSolveButton();
        });
        add(resetButton);
    }

    private void addShuffleButton() {
        shuffleButton = new JButton(Move.SHUFFLE.getSymbol());
        shuffleButton.addActionListener(e -> {
            cube.shuffle();
            resetSolveButton();
        });
        add(shuffleButton);
    }

    private void addSolveButton() {
        solveButton = new JButton(teachMeStr);
        solveButton.addActionListener(e -> {
            if (solveButton.getText().equals(teachMeStr)) {
                solveButton.setText(solveAloneStr);
                solver.solve();
            } else {
                resetSolveButton();
            }
        });
        add(solveButton);
    }

    private void resetSolveButton() {
        solveButton.setText(teachMeStr);
        solver.clear();
    }
}
