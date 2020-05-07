package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class ShuffleBar extends JPanel {

    private Cube cube;
    private Solver solver;
    private JButton shuffleButton;
    private JButton resetButton;
    private JButton solveButton;

    public ShuffleBar(Cube cube, Solver solver) {
        this.cube = cube;
        this.solver = solver;
        setLayout(new FlowLayout());
        addResetButton();
        addShuffleButton();
        addSolveButton();
    }

    private void addResetButton() {
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> cube.reset());
        add(resetButton);
    }

    private void addShuffleButton() {
        shuffleButton = new JButton(Move.SHUFFLE.getSymbol());
        shuffleButton.addActionListener(e -> cube.shuffle());
        add(shuffleButton);
    }

    private void addSolveButton() {
        solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> solver.solve());
        add(solveButton);
    }
}
