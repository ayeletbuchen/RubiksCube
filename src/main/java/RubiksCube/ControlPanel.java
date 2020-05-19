package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public ControlPanel(Cube cube, Solver solver, DirectionLabel directionLabel) {
        setLayout(new BorderLayout());
        add(directionLabel, BorderLayout.NORTH);
        add(new RotationsPanel(cube), BorderLayout.CENTER);
        add(new ShuffleBar(cube, solver, directionLabel), BorderLayout.SOUTH);
    }
}
