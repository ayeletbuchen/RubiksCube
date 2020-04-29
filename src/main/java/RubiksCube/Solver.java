package RubiksCube;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Solver extends Stack<Move> implements Observer<Move> {

    private Cube cube;
    private Face upFace;
    private Face leftFace;
    private Face frontFace;
    private Face rightFace;
    private Face backFace;
    private Face downFace;
    private boolean userSolving;
    private Stack<Move> computerMoveStack;
    private Stack<Move> userMoveStack;
    private HashMap<Square, Square> adjacentEdgesMap;
    private HashMap<Square, ArrayList<Square>> adjacentCornersMap;

    public Solver(Cube cube) {
        this.cube = cube;
        upFace = cube.getUpFace();
        leftFace = cube.getLeftFace();
        frontFace = cube.getFrontFace();
        rightFace = cube.getRightFace();
        backFace = cube.getBackFace();
        downFace = cube.getDownFace();
        userSolving = true;
        adjacentEdgesMap = new HashMap<>();
        adjacentCornersMap = new HashMap<>();
        computerMoveStack = new Stack<>();
        userMoveStack = new Stack<>();
        setCounterMoves();
        setAdjacentEdgesMap();
    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(Move move) {
        if (move.equals(Move.SHUFFLE)) {
            userSolving = false;
            solve();
            // while (!computerMoveStack.isEmpty()) {
            //     userMoveStack.push(computerMoveStack.pop());
            // }
            // userSolving = true;
        } else if (move.equals(Move.RESET)) {
            userMoveStack.clear();
            computerMoveStack.clear();
        } else if (!userSolving) {
            computerMoveStack.push(move);
        } else if (!userMoveStack.isEmpty()) {
            Move nextMove = userMoveStack.peek();
            if (move.equals(nextMove)) {
                userMoveStack.pop();
            } else {
                userMoveStack.push(move.getCounterMove());
            }
            if (userMoveStack.isEmpty()) {
                System.out.println("Great job!");
            } else {
                System.out.println(userMoveStack.peek().getPrompt());
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public void solve() {
        solveTopLayer();
    }

    private void solveTopLayer() {
        moveWhiteCenterSquareToUpFace();
        createWhiteCross();
    }

    private void moveWhiteCenterSquareToUpFace() {
        if (!upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            if (leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                    .equals(CubeColors.UP_FACE_COLOR.getColor())) {
                cube.turnCubeClockwiseAlongZAxis();
            } else if (frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor())) {
                cube.turnCubeUp();
            } else if (rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor())) {
                cube.turnCubeCounterclockwiseAlongZAxis();
            } else if (backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor())) {
                cube.turnCubeDown();
            } else {
                cube.doubleVerticalCubeTurn();
            }
        }
    }

    private void createWhiteCross() {
//        while (!upFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
//                .equals(CubeColors.UP_FACE_COLOR.getColor())
//                || !upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()].getColor()
//                .equals(CubeColors.UP_FACE_COLOR.getColor())
//                || !upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()].getColor()
//                .equals(CubeColors.UP_FACE_COLOR.getColor())
//                || !upFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
//                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteEdgeSquaresFromDownFaceToUpFace();
            moveWhiteEdgeSquaresFromBottomLayerToUpFace();
            moveWhiteEdgeSquaresFromMiddleLayerToUpFace();
//        }
    }

    private void moveWhiteEdgeSquaresFromDownFaceToUpFace() {
        if (downFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
        if (downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (downFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteEdgeSquaresFromBottomLayerToUpFace() {
        if (frontFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace();
        }
        if (leftFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromLeftFaceBottomRowMiddleColumnToUpFace();
        }
        if (backFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromBackFaceBottomRowMiddleColumnToUpFace();
        }
        if (rightFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            moveWhiteSquareFromRightFaceBottomRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteEdgeSquaresFromMiddleLayerToUpFace() {
        boolean movedPositionedSquare;
        if (frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateLeftFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateLeftFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
        if (frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateRightFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateRightFaceClockwise();
            }
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
        if (leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateBackFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateBackFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateFrontFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateFrontFaceClockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateFrontFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateFrontFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateBackFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateBackFaceClockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateRightFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateRightFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
        if (backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()].getColor()
                .equals(CubeColors.UP_FACE_COLOR.getColor())) {
            movedPositionedSquare = upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]
                    .getColor().equals(CubeColors.UP_FACE_COLOR.getColor());
            cube.rotateLeftFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateLeftFaceClockwise();
            }
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap
                .get(frontFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()])
                .getColor();

        if (adjacentColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromLeftFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap
                .get(leftFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()])
                .getColor();

        if (adjacentColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromBackFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap
                .get(backFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()])
                .getColor();

        if (adjacentColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromRightFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap
                .get(rightFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()])
                .getColor();

        if (adjacentColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromBottomLayerToUpFace(Face originalFace) {
        cube.rotateDownFaceCounterclockwise();

        if (originalFace.equals(leftFace)) {
            cube.rotateBackFaceCounterclockwise();
            cube.rotateLeftFaceClockwise();
            cube.rotateBackFaceClockwise();
        } else if (originalFace.equals(frontFace)) {
            cube.rotateLeftFaceCounterclockwise();
            cube.rotateFrontFaceClockwise();
            cube.rotateLeftFaceClockwise();
        } else if (originalFace.equals(rightFace)) {
            cube.rotateFrontFaceCounterclockwise();
            cube.rotateRightFaceClockwise();
            cube.rotateFrontFaceClockwise();
        } else if (originalFace.equals(backFace)) {
            cube.rotateRightFaceCounterclockwise();
            cube.rotateBackFaceClockwise();
            cube.rotateRightFaceClockwise();
        }
    }

    private void moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap
                .get(downFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()])
                .getColor();

        if (adjacentEdgeColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(CubeColors.FRONT_FACE_COLOR.getColor())) {
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            cube.doubleRotateBackFace();
        }
    }

    private void moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap
                .get(downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()])
                .getColor();

        if (adjacentEdgeColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(CubeColors.FRONT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateBackFace();
        }
    }

    private void moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap
                .get(downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()])
                .getColor();

        if (adjacentEdgeColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(CubeColors.FRONT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateBackFace();
        }
    }

    private void moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap
                .get(downFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()])
                .getColor();

        if (adjacentEdgeColor.equals(CubeColors.LEFT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(CubeColors.FRONT_FACE_COLOR.getColor())) {
            cube.doubleRotateDownFace();
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(CubeColors.RIGHT_FACE_COLOR.getColor())) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(CubeColors.BACK_FACE_COLOR.getColor())) {
            cube.doubleRotateBackFace();
        }
    }

    private void setCounterMoves() {
        Move.U.setCounterMove(Move.U_PRIME);
        Move.L.setCounterMove(Move.L_PRIME);
        Move.F.setCounterMove(Move.F_PRIME);
        Move.R.setCounterMove(Move.R_PRIME);
        Move.B.setCounterMove(Move.B_PRIME);
        Move.D.setCounterMove(Move.D_PRIME);
        Move.U_PRIME.setCounterMove(Move.U);
        Move.L_PRIME.setCounterMove(Move.L);
        Move.F_PRIME.setCounterMove(Move.F);
        Move.R_PRIME.setCounterMove(Move.R);
        Move.B_PRIME.setCounterMove(Move.B);
        Move.D_PRIME.setCounterMove(Move.D);
        Move.M.setCounterMove(Move.M_PRIME);
        Move.E.setCounterMove(Move.E_PRIME);
        Move.S.setCounterMove(Move.S_PRIME);
        Move.M_PRIME.setCounterMove(Move.M);
        Move.E_PRIME.setCounterMove(Move.E);
        Move.S_PRIME.setCounterMove(Move.S);
        Move.Y_PRIME.setCounterMove(Move.Y);
        Move.Y.setCounterMove(Move.Y_PRIME);
        Move.X.setCounterMove(Move.X_PRIME);
        Move.X_PRIME.setCounterMove(Move.X);
        Move.Z.setCounterMove(Move.Z_PRIME);
        Move.Z_PRIME.setCounterMove(Move.Z);
        Move.SHUFFLE.setCounterMove(Move.RESET);
        Move.RESET.setCounterMove(Move.SHUFFLE);
    }

    private void setAdjacentEdgesMap() {
        adjacentEdgesMap.put(downFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                frontFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(frontFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                downFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(leftFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]);
        adjacentEdgesMap.put(downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()],
                leftFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(rightFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]);
        adjacentEdgesMap.put(downFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()],
                rightFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(backFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                downFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(downFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                backFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(leftFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]);
        adjacentEdgesMap.put(upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()],
                leftFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(frontFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                upFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(upFace.squares[CubeValues.BOTTOM_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                frontFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(rightFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]);
        adjacentEdgesMap.put(upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()],
                rightFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(backFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                upFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(upFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()],
                backFace.squares[CubeValues.TOP_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()]);
        adjacentEdgesMap.put(leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()],
                backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]);
        adjacentEdgesMap.put(backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()],
                leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]);
        adjacentEdgesMap.put(leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()],
                frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]);
        adjacentEdgesMap.put(frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()],
                leftFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]);
        adjacentEdgesMap.put(frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()],
                rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]);
        adjacentEdgesMap.put(rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()],
                frontFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]);
        adjacentEdgesMap.put(rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()],
                backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()]);
        adjacentEdgesMap.put(backFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.LEFT_COLUMN.getValue()],
                rightFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.RIGHT_COLUMN.getValue()]);
    }

//
//
//    corners
//    down top left pairs with front bottom left and left face bottom right;
//    down top right = front bottom right = right bottom left;
//    down bottom left = left bottom left = back bottom right;
//    down bottom right = right bottom right = back bottom left
//    up top left = left top left = back top right;
//    up top right = right top right = back top left;
//    up bottom left = left top right = front top left;
//    up bottom right = right top left = front top right;
}
