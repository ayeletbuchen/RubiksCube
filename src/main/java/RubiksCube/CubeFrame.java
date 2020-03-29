package RubiksCube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // cube.sliceMiddleColumnsUpwards();
                // cube.rotateLeftFaceCounterclockwise();
                // cube.rotateRightFaceClockwise();

                // cube.sliceMiddleRowsClockwise();
                // cube.rotateTopFaceClockwise();
                // cube.rotateDownFaceCounterclockwise(); // TODO fix issue here
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setContentPane(root);
    }
}
