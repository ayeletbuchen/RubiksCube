package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class RotationsPanel extends JPanel {

    private Cube cube;
    private final int ROWS = 4;
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

    private JButton leftTurnButton;
    private JButton rightTurnButton;
    private JButton upTurnButton;
    private JButton downTurnButton;

    public RotationsPanel(Cube cube) {
        this.cube = cube;
        setLayout(new GridLayout(ROWS, COLS));
        addClockwiseFaceRotationButtons();
        addCounterclockwiseFaceRotationButtons();
        addSliceButtons();
        addCubeTurnButtons();
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

    private void addCubeTurnButtons() {
        addLeftTurnButton();
        addRightTurnButton();
        addUpTurnButton();
        addDownTurnButton();
    }

    private void addUpClockwiseButton() {
        upClockwiseButton = new JButton(Move.U.getSymbol());
        upClockwiseButton.addActionListener(e -> cube.rotateUpFaceClockwise());
        add(upClockwiseButton);
    }

    private void addLeftClockwiseButton() {
        leftClockwiseButton = new JButton(Move.L.getSymbol());
        leftClockwiseButton.addActionListener(e -> cube.rotateLeftFaceClockwise());
        add(leftClockwiseButton);
    }

    private void addFrontClockwiseButton() {
        frontClockwiseButton = new JButton(Move.F.getSymbol());
        frontClockwiseButton.addActionListener(e -> cube.rotateFrontFaceClockwise());
        add(frontClockwiseButton);
    }

    private void addRightClockwiseButton() {
        rightClockwiseButton = new JButton(Move.R.getSymbol());
        rightClockwiseButton.addActionListener(e -> cube.rotateRightFaceClockwise());
        add(rightClockwiseButton);
    }

    private void addBackClockwiseButton() {
        backClockwiseButton = new JButton(Move.B.getSymbol());
        backClockwiseButton.addActionListener(e -> cube.rotateBackFaceClockwise());
        add(backClockwiseButton);
    }

    private void addDownClockwiseButton() {
        downClockwiseButton = new JButton(Move.D.getSymbol());
        downClockwiseButton.addActionListener(e -> cube.rotateDownFaceClockwise());
        add(downClockwiseButton);
    }

    private void addUpCounterclockwiseButton() {
        upCounterclockwiseButton = new JButton(Move.U_PRIME.getSymbol());
        upCounterclockwiseButton.addActionListener(e -> cube.rotateUpFaceCounterclockwise());
        add(upCounterclockwiseButton);
    }

    private void addLeftCounterclockwiseButton() {
        leftCounterclockwiseButton = new JButton(Move.L_PRIME.getSymbol());
        leftCounterclockwiseButton.addActionListener(e -> cube.rotateLeftFaceCounterclockwise());
        add(leftCounterclockwiseButton);
    }

    private void addFrontCounterclockwiseButton() {
        frontCounterclockwiseButton = new JButton(Move.F_PRIME.getSymbol());
        frontCounterclockwiseButton.addActionListener(e -> cube.rotateFrontFaceCounterclockwise());
        add(frontCounterclockwiseButton);
    }

    private void addRightCounterclockwiseButton() {
        rightCounterclockwiseButton = new JButton(Move.R_PRIME.getSymbol());
        rightCounterclockwiseButton.addActionListener(e -> cube.rotateRightFaceCounterclockwise());
        add(rightCounterclockwiseButton);
    }

    private void addBackCounterclockwiseButton() {
        backCounterclockwiseButton = new JButton(Move.B_PRIME.getSymbol());
        backCounterclockwiseButton.addActionListener(e -> cube.rotateBackFaceCounterclockwise());
        add(backCounterclockwiseButton);
    }

    private void addDownCounterclockwiseButton() {
        downCounterclockwiseButton = new JButton(Move.D_PRIME.getSymbol());
        downCounterclockwiseButton.addActionListener(e -> cube.rotateDownFaceCounterclockwise());
        add(downCounterclockwiseButton);
    }

    private void addMiddleClockwiseButton() {
        middleClockwiseButton = new JButton(Move.M.getSymbol());
        middleClockwiseButton.addActionListener(e -> cube.sliceMiddleLayerClockwise());
        add(middleClockwiseButton);
    }

    private void addMiddleCounterClockwiseButton() {
        middleCounterclockwiseButton = new JButton(Move.M_PRIME.getSymbol());
        middleCounterclockwiseButton.addActionListener(e -> cube.sliceMiddleLayerCounterclockwise());
        add(middleCounterclockwiseButton);
    }

    private void addEquatorialClockwiseButton() {
        equatorialClockwiseButton = new JButton(Move.E.getSymbol());
        equatorialClockwiseButton.addActionListener(e -> cube.sliceEquatorialLayerClockwise());
        add(equatorialClockwiseButton);
    }

    private void addEquatorialCounterClockwiseButton() {
        equatorialCounterclockwiseButton = new JButton(Move.E_PRIME.getSymbol());
        equatorialCounterclockwiseButton.addActionListener(e -> cube.sliceEquatorialLayerCounterclockwise());
        add(equatorialCounterclockwiseButton);
    }

    private void addStandingClockwiseButton() {
        standingClockwiseButton = new JButton(Move.S.getSymbol());
        standingClockwiseButton.addActionListener(e -> cube.sliceStandingLayerClockwise());
        add(standingClockwiseButton);
    }

    private void addStandingCounterClockwiseButton() {
        standingCounterclockwiseButton = new JButton(Move.S_PRIME.getSymbol());
        standingCounterclockwiseButton.addActionListener(e -> cube.sliceStandingLayerCounterclockwise());
        add(standingCounterclockwiseButton);
    }

    private void addLeftTurnButton() {
        leftTurnButton = new JButton(Move.CUBE_LEFT_TURN.getSymbol());
        leftTurnButton.addActionListener(e -> cube.turnCubeLeft());
        add(leftTurnButton);
    }

    private void addRightTurnButton() {
        rightTurnButton = new JButton(Move.CUBE_RIGHT_TURN.getSymbol());
        rightTurnButton.addActionListener(e -> cube.turnCubeRight());
        add(rightTurnButton);
    }

    private void addUpTurnButton() {
        upTurnButton = new JButton(Move.CUBE_UP_TURN.getSymbol());
        upTurnButton.addActionListener(e -> cube.turnCubeUp());
        add(upTurnButton);
    }

    private void addDownTurnButton() {
        downTurnButton = new JButton(Move.CUBE_DOWN_TURN.getSymbol());
        downTurnButton.addActionListener(e -> cube.turnCubeDown());
        add(downTurnButton);
    }
}
