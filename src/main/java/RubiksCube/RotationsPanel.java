package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class RotationsPanel extends JPanel {

    private Cube cube;
    private final int ROWS = 3;
    private final int COLS = 6;

    private JButton upClockwiseButton;
    private JButton leftClockwiseButton;
    private JButton frontClockwiseButton;
    private JButton rightClockwiseButton;
    private JButton backClockwiseButton;
    private JButton downClockwiseButton;

    private JButton upCounterclockwiseButton;
    private JButton leftCounterclockwiseButton;
    private JButton frontCounterclockwiseButton;
    private JButton rightCounterclockwiseButton;
    private JButton backCounterclockwiseButton;
    private JButton downCounterclockwiseButton;

    private JButton middleClockwiseButton;
    private JButton middleCounterclockwiseButton;
    private JButton equatorialClockwiseButton;
    private JButton equatorialCounterclockwiseButton;
    private JButton standingClockwiseButton;
    private JButton standingCounterclockwiseButton;

    private final String UP = "U";
    private final String LEFT = "L";
    private final String FRONT = "F";
    private final String RIGHT = "R";
    private final String BACK = "B";
    private final String DOWN = "D";
    private final String MIDDLE = "M";
    private final String EQUATORIAL = "E";
    private final String STANDING = "S";
    private final String COUNTERCLOCKWISE = "'";

    public RotationsPanel(Cube cube) {
        this.cube = cube;
        setLayout(new GridLayout(ROWS, COLS));
        addClockwiseFaceRotationButtons();
        addCounterclockwiseFaceRotationButtons();
        addSliceButtons();
    }

    private void addClockwiseFaceRotationButtons() {
        addUpClockwiseButton();
        addLeftClockwiseButton();
        addFrontClockwiseButton();
        addRightClockwiseButton();
        addBackClockwiseButton();
        addDownClockwiseButton();
    }

    private void addCounterclockwiseFaceRotationButtons() {
        addUpCounterclockwiseButton();
        addLeftCounterclockwiseButton();
        addFrontCounterclockwiseButton();
        addRightCounterclockwiseButton();
        addBackCounterclockwiseButton();
        addDownCounterclockwiseButton();
    }

    private void addSliceButtons() {
        addMiddleClockwiseButton();
        addMiddleCounterClockwiseButton();
        addEquatorialClockwiseButton();
        addEquatorialCounterClockwiseButton();
        addStandingClockwiseButton();
        addStandingCounterClockwiseButton();
    }

    private void addUpClockwiseButton() {
        upClockwiseButton = new JButton(UP);
        upClockwiseButton.addActionListener(e -> cube.rotateUpFaceClockwise());
        add(upClockwiseButton);
    }

    private void addLeftClockwiseButton() {
        leftClockwiseButton = new JButton(LEFT);
        leftClockwiseButton.addActionListener(e -> cube.rotateLeftFaceClockwise());
        add(leftClockwiseButton);
    }

    private void addFrontClockwiseButton() {
        frontClockwiseButton = new JButton(FRONT);
        frontClockwiseButton.addActionListener(e -> cube.rotateFrontFaceClockwise());
        add(frontClockwiseButton);
    }

    private void addRightClockwiseButton() {
        rightClockwiseButton = new JButton(RIGHT);
        rightClockwiseButton.addActionListener(e -> cube.rotateRightFaceClockwise());
        add(rightClockwiseButton);
    }

    private void addBackClockwiseButton() {
        backClockwiseButton = new JButton(BACK);
        backClockwiseButton.addActionListener(e -> cube.rotateBackFaceClockwise());
        add(backClockwiseButton);
    }

    private void addDownClockwiseButton() {
        downClockwiseButton = new JButton(DOWN);
        downClockwiseButton.addActionListener(e -> cube.rotateDownFaceClockwise());
        add(downClockwiseButton);
    }

    private void addUpCounterclockwiseButton() {
        upCounterclockwiseButton = new JButton(UP + COUNTERCLOCKWISE);
        upCounterclockwiseButton.addActionListener(e -> cube.rotateUpFaceCounterclockwise());
        add(upCounterclockwiseButton);
    }

    private void addLeftCounterclockwiseButton() {
        leftCounterclockwiseButton = new JButton(LEFT + COUNTERCLOCKWISE);
        leftCounterclockwiseButton.addActionListener(e -> cube.rotateLeftFaceCounterclockwise());
        add(leftCounterclockwiseButton);
    }

    private void addFrontCounterclockwiseButton() {
        frontCounterclockwiseButton = new JButton(FRONT + COUNTERCLOCKWISE);
        frontCounterclockwiseButton.addActionListener(e -> cube.rotateFrontFaceCounterclockwise());
        add(frontCounterclockwiseButton);
    }

    private void addRightCounterclockwiseButton() {
        rightCounterclockwiseButton = new JButton(RIGHT + COUNTERCLOCKWISE);
        rightCounterclockwiseButton.addActionListener(e -> cube.rotateRightFaceCounterclockwise());
        add(rightCounterclockwiseButton);
    }

    private void addBackCounterclockwiseButton() {
        backCounterclockwiseButton = new JButton(BACK + COUNTERCLOCKWISE);
        backCounterclockwiseButton.addActionListener(e -> cube.rotateBackFaceCounterclockwise());
        add(backCounterclockwiseButton);
    }

    private void addDownCounterclockwiseButton() {
        downCounterclockwiseButton = new JButton(DOWN + COUNTERCLOCKWISE);
        downCounterclockwiseButton.addActionListener(e -> cube.rotateDownFaceCounterclockwise());
        add(downCounterclockwiseButton);
    }

    private void addMiddleClockwiseButton() {
        middleClockwiseButton = new JButton(MIDDLE);
        middleClockwiseButton.addActionListener(e -> cube.sliceMiddleLayerClockwise());
        add(middleClockwiseButton);
    }

    private void addMiddleCounterClockwiseButton() {
        middleCounterclockwiseButton = new JButton(MIDDLE + COUNTERCLOCKWISE);
        middleCounterclockwiseButton.addActionListener(e -> cube.sliceMiddleLayerCounterclockwise());
        add(middleCounterclockwiseButton);
    }

    private void addEquatorialClockwiseButton() {
        equatorialClockwiseButton = new JButton(EQUATORIAL);
        equatorialClockwiseButton.addActionListener(e -> cube.sliceEquatorialLayerClockwise());
        add(equatorialClockwiseButton);
    }

    private void addEquatorialCounterClockwiseButton() {
        equatorialCounterclockwiseButton = new JButton(EQUATORIAL + COUNTERCLOCKWISE);
        equatorialCounterclockwiseButton.addActionListener(e -> cube.sliceEquatorialLayerCounterclockwise());
        add(equatorialCounterclockwiseButton);
    }

    private void addStandingClockwiseButton() {
        standingClockwiseButton = new JButton(STANDING);
        standingClockwiseButton.addActionListener(e -> cube.sliceStandingLayerClockwise());
        add(standingClockwiseButton);
    }

    private void addStandingCounterClockwiseButton() {
        standingCounterclockwiseButton = new JButton(STANDING + COUNTERCLOCKWISE);
        standingCounterclockwiseButton.addActionListener(e -> cube.sliceStandingLayerCounterclockwise());
        add(standingCounterclockwiseButton);
    }
}
