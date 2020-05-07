package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private Cube cube;
    private Solver solver;

    public ControlPanel(Cube cube, Solver solver) {
        this.cube = cube;
        this.solver = solver;
        setLayout(new BorderLayout());
        add(new RotationsPanel(cube), BorderLayout.CENTER);
        add(new ShuffleBar(cube, solver), BorderLayout.SOUTH);
    }
}
