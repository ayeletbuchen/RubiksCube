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
    private final int TOP_ROW = CubeValues.TOP_ROW.getValue();
    private final int MIDDLE_ROW = CubeValues.MIDDLE_ROW.getValue();
    private final int BOTTOM_ROW = CubeValues.BOTTOM_ROW.getValue();
    private final int LEFT_COLUMN = CubeValues.LEFT_COLUMN.getValue();
    private final int MIDDLE_COLUMN = CubeValues.MIDDLE_COLUMN.getValue();
    private final int RIGHT_COLUMN = CubeValues.RIGHT_COLUMN.getValue();
    private final Color UP_FACE_COLOR = CubeColors.UP_FACE_COLOR.getColor();
    private final Color LEFT_FACE_COLOR = CubeColors.LEFT_FACE_COLOR.getColor();
    private final Color RIGHT_FACE_COLOR = CubeColors.RIGHT_FACE_COLOR.getColor();
    private final Color FRONT_FACE_COLOR = CubeColors.FRONT_FACE_COLOR.getColor();
    private final Color BACK_FACE_COLOR = CubeColors.BACK_FACE_COLOR.getColor();
    private final Color DOWN_FACE_COLOR = CubeColors.DOWN_FACE_COLOR.getColor();

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
        if (!upFace.squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            if (leftFace.squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
                cube.turnCubeClockwiseAlongZAxis();
            } else if (frontFace.squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
                cube.turnCubeUp();
            } else if (rightFace.squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
                cube.turnCubeCounterclockwiseAlongZAxis();
            } else if (backFace.squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
                cube.turnCubeDown();
            } else {
                cube.doubleVerticalCubeTurn();
            }
        }
    }

    private void createWhiteCross() {
//        while (!upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor()
//                .equals(UP_FACE_COLOR())
//                || !upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor()
//                .equals(UP_FACE_COLOR())
//                || !upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor()
//                .equals(UP_FACE_COLOR())
//                || !upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor()
//                .equals(UP_FACE_COLOR())) {
            moveWhiteEdgeSquaresFromDownFaceToUpFace();
            moveWhiteEdgeSquaresFromBottomLayerToUpFace();
            moveWhiteEdgeSquaresFromMiddleLayerToUpFace();
//        }
    }

    private void moveWhiteEdgeSquaresFromDownFaceToUpFace() {
        if (downFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
        if (downFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (downFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteEdgeSquaresFromBottomLayerToUpFace() {
        if (frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace();
        }
        if (leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromLeftFaceBottomRowMiddleColumnToUpFace();
        }
        if (backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromBackFaceBottomRowMiddleColumnToUpFace();
        }
        if (rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromRightFaceBottomRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteEdgeSquaresFromMiddleLayerToUpFace() {
        boolean movedPositionedSquare;

        if (frontFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateLeftFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateLeftFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
        if (frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateRightFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateRightFaceClockwise();
            }
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
        if (leftFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateBackFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateBackFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateFrontFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateFrontFaceClockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (rightFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateFrontFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateFrontFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateBackFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateBackFaceClockwise();
            }
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (backFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateRightFaceClockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateRightFaceCounterclockwise();
            }
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
        if (backFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            movedPositionedSquare = upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR);
            cube.rotateLeftFaceCounterclockwise();
            cube.rotateDownFaceClockwise();
            if (movedPositionedSquare) {
                cube.rotateLeftFaceClockwise();
            }
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap.get(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromLeftFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap
                .get(leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN])
                .getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromBackFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap.get(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            moveWhiteSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromRightFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap.get(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            moveWhiteSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            moveWhiteSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
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
        Color adjacentEdgeColor = adjacentEdgesMap.get(downFace.squares[TOP_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentEdgeColor.equals(LEFT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(FRONT_FACE_COLOR)) {
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(RIGHT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(BACK_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            cube.doubleRotateBackFace();
        }
    }

    private void moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap.get(downFace.squares[MIDDLE_ROW][LEFT_COLUMN]).getColor();

        if (adjacentEdgeColor.equals(LEFT_FACE_COLOR)) {
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(FRONT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(RIGHT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(BACK_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateBackFace();
        }
    }

    private void moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap.get(downFace.squares[MIDDLE_ROW][RIGHT_COLUMN]).getColor();

        if (adjacentEdgeColor.equals(LEFT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(FRONT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(RIGHT_FACE_COLOR)) {
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(BACK_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateBackFace();
        }
    }

    private void moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentEdgeColor = adjacentEdgesMap.get(downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentEdgeColor.equals(LEFT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            cube.doubleRotateLeftFace();
        } else if (adjacentEdgeColor.equals(FRONT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            cube.doubleRotateFrontFace();
        } else if (adjacentEdgeColor.equals(RIGHT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            cube.doubleRotateRightFace();
        } else if (adjacentEdgeColor.equals(BACK_FACE_COLOR)) {
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
        adjacentEdgesMap.put(downFace.squares[TOP_ROW][MIDDLE_COLUMN], frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        adjacentEdgesMap.put(downFace.squares[MIDDLE_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        adjacentEdgesMap.put(downFace.squares[MIDDLE_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(leftFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        adjacentEdgesMap.put(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], leftFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(frontFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], frontFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(rightFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        adjacentEdgesMap.put(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN], rightFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(backFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(upFace.squares[TOP_ROW][MIDDLE_COLUMN], backFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        adjacentEdgesMap.put(leftFace.squares[MIDDLE_ROW][LEFT_COLUMN], backFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        adjacentEdgesMap.put(backFace.squares[MIDDLE_ROW][RIGHT_COLUMN], leftFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        adjacentEdgesMap.put(leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN], frontFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        adjacentEdgesMap.put(frontFace.squares[MIDDLE_ROW][LEFT_COLUMN], leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        adjacentEdgesMap.put(frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN], rightFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        adjacentEdgesMap.put(rightFace.squares[MIDDLE_ROW][LEFT_COLUMN], frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        adjacentEdgesMap.put(rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN], backFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        adjacentEdgesMap.put(backFace.squares[MIDDLE_ROW][LEFT_COLUMN], rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
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
