package RubiksCube;

import java.awt.*;
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
    private HashMap<Square, Square[]> adjacentCornersMap;
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
        setAdjacentCornersMap();
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
        putWhiteCornersInPlace();
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
        while (!upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)
                || !upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)
                || !upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                || !upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)
                || !leftFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(LEFT_FACE_COLOR)
                || !frontFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(FRONT_FACE_COLOR)
                || !rightFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(RIGHT_FACE_COLOR)
                || !backFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(BACK_FACE_COLOR)) {
            orientUpFaceWhiteEdges();
            moveWhiteEdgeSquaresFromDownFaceToUpFace();
            moveAllWhiteEdgeSquaresFromBottomLayerToUpFace();
            moveWhiteEdgeSquaresFromMiddleLayerToUpFace();
            moveWhiteEdgeSquaresFromTopLayerToUpFace();
        }
    }

    private void putWhiteCornersInPlace() {
        Square upFaceTopLeftSquare = upFace.squares[TOP_ROW][LEFT_COLUMN];
        Square upFaceTopRightSquare = upFace.squares[TOP_ROW][RIGHT_COLUMN];
        Square upFaceBottomLeftSquare = upFace.squares[BOTTOM_ROW][LEFT_COLUMN];
        Square upFaceBottomRightSquare = upFace.squares[BOTTOM_ROW][RIGHT_COLUMN];
        Square[] upFaceTopLeftCorner = adjacentCornersMap.get(upFaceTopLeftSquare);
        Square[] upFaceTopRightCorner = adjacentCornersMap.get(upFaceTopRightSquare);
        Square[] upFaceBottomLeftCorner = adjacentCornersMap.get(upFaceBottomLeftSquare);
        Square[] upFaceBottomRightCorner = adjacentCornersMap.get(upFaceBottomRightSquare);

        Square downFaceTopLeftSquare = downFace.squares[TOP_ROW][LEFT_COLUMN];
        Square downFaceTopRightSquare = downFace.squares[TOP_ROW][RIGHT_COLUMN];
        Square downFaceBottomLeftSquare = downFace.squares[BOTTOM_ROW][LEFT_COLUMN];
        Square downFaceBottomRightSquare = downFace.squares[BOTTOM_ROW][RIGHT_COLUMN];
        Square[] downFaceTopLeftCorner = adjacentCornersMap.get(downFaceTopLeftSquare);
        Square[] downFaceTopRightCorner = adjacentCornersMap.get(downFaceTopRightSquare);
        Square[] downFaceBottomLeftCorner = adjacentCornersMap.get(downFaceBottomLeftSquare);
        Square[] downFaceBottomRightCorner = adjacentCornersMap.get(downFaceBottomRightSquare);

        while(!(upFaceTopLeftSquare.getColor().equals(UP_FACE_COLOR)
                && upFaceTopLeftCorner[0].getColor().equals(upFaceTopLeftCorner[0].getORIGINAL_COLOR())
                && upFaceTopLeftCorner[1].getColor().equals(upFaceTopLeftCorner[1].getORIGINAL_COLOR()))
            || !(upFaceTopRightSquare.getColor().equals(UP_FACE_COLOR)
                && upFaceTopRightCorner[0].getColor().equals(upFaceTopRightCorner[0].getORIGINAL_COLOR())
                && upFaceTopRightCorner[1].getColor().equals(upFaceTopRightCorner[1].getORIGINAL_COLOR()))
            || !(upFaceBottomLeftSquare.getColor().equals(UP_FACE_COLOR)
                && upFaceBottomLeftCorner[0].getColor().equals(upFaceBottomLeftCorner[0].getORIGINAL_COLOR())
                && upFaceBottomLeftCorner[1].getColor().equals(upFaceBottomLeftCorner[1].getORIGINAL_COLOR()))
            || !(upFaceBottomRightSquare.getColor().equals(UP_FACE_COLOR)
                && upFaceBottomRightCorner[0].getColor().equals(upFaceBottomRightCorner[0].getORIGINAL_COLOR())
                && upFaceBottomRightCorner[1].getColor().equals(upFaceBottomRightCorner[1].getORIGINAL_COLOR()))) {

            if (downFaceTopLeftSquare.getColor().equals(UP_FACE_COLOR)
                || downFaceTopLeftCorner[0].getColor().equals(UP_FACE_COLOR)
                || downFaceTopLeftCorner[1].getColor().equals(UP_FACE_COLOR)) {
                orientWhiteCornerFromDownFace(downFaceTopLeftSquare, downFaceTopLeftCorner, TOP_ROW, LEFT_COLUMN);
            }
            if (downFaceTopRightSquare.getColor().equals(UP_FACE_COLOR)
                || downFaceTopRightCorner[0].getColor().equals(UP_FACE_COLOR)
                || downFaceTopRightCorner[1].getColor().equals(UP_FACE_COLOR)) {
                orientWhiteCornerFromDownFace(downFaceTopRightSquare, downFaceTopRightCorner, TOP_ROW, RIGHT_COLUMN);
            }
            if (downFaceBottomLeftSquare.getColor().equals(UP_FACE_COLOR)
                || downFaceBottomLeftCorner[0].getColor().equals(UP_FACE_COLOR)
                || downFaceBottomLeftCorner[1].getColor().equals(UP_FACE_COLOR)) {
                orientWhiteCornerFromDownFace(downFaceBottomLeftSquare, downFaceBottomLeftCorner,
                        BOTTOM_ROW, LEFT_COLUMN);
            }
            if (downFaceBottomRightSquare.getColor().equals(UP_FACE_COLOR)
                || downFaceBottomRightCorner[0].getColor().equals(UP_FACE_COLOR)
                || downFaceBottomRightCorner[1].getColor().equals(UP_FACE_COLOR)) {
                orientWhiteCornerFromDownFace(downFaceBottomRightSquare, downFaceBottomRightCorner,
                        BOTTOM_ROW, RIGHT_COLUMN);
            }
        }
    }

    private void orientWhiteCornerFromDownFace(Square square, Square[] corner, int downFaceRow, int downFaceCol) {
        if (square.getColor().equals(FRONT_FACE_COLOR)
            || corner[0].getColor().equals(FRONT_FACE_COLOR)
            || corner[1].getColor().equals(FRONT_FACE_COLOR)) {

            if (square.getColor().equals(LEFT_FACE_COLOR)
                || corner[0].getColor().equals(LEFT_FACE_COLOR)
                || corner[1].getColor().equals(LEFT_FACE_COLOR)) {
                orientUpFaceBottomLeftCorner(downFaceRow, downFaceCol);
            } else {
                orientUpFaceBottomRightCorner(downFaceRow, downFaceCol);
            }
        } else if (square.getColor().equals(BACK_FACE_COLOR)
            || corner[0].getColor().equals(BACK_FACE_COLOR)
            || corner[1].getColor().equals(BACK_FACE_COLOR)) {

            if (square.getColor().equals(LEFT_FACE_COLOR)
                    || corner[0].getColor().equals(LEFT_FACE_COLOR)
                    || corner[1].getColor().equals(LEFT_FACE_COLOR)) {
                // orientUpFaceBackLeftCorner();
            } else {
                // orientUpFaceBackRightCorner();
            }
        }
    }

    private void orientUpFaceBottomLeftCorner(int downFaceRow, int downFaceCol) {
        if (downFaceRow == TOP_ROW) {
            if (downFaceCol == RIGHT_COLUMN) {
                cube.rotateDownFaceCounterclockwise();
            }
        } else if (downFaceRow == BOTTOM_ROW) {
            if (downFaceCol == LEFT_COLUMN) {
                cube.rotateDownFaceClockwise();
            } else {
                cube.doubleRotateDownFace();
            }
        }

        cube.doubleVerticalCubeTurn();
        cube.doubleHorizontalCubeTurn();

        while(!(upFace.squares[BOTTOM_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)
            && frontFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(FRONT_FACE_COLOR)
            && leftFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(LEFT_FACE_COLOR))) {
            orientUpFaceCorner();
        }

        cube.doubleHorizontalCubeTurn();
        cube.doubleVerticalCubeTurn();
    }

    private void orientUpFaceBottomRightCorner(int downFaceRow, int downFaceCol) {
        if (downFaceRow == TOP_ROW) {
            if (downFaceCol == LEFT_COLUMN) {
                cube.rotateDownFaceClockwise();
            }
        } else if (downFaceRow == BOTTOM_ROW) {
            if (downFaceCol == LEFT_COLUMN) {
                cube.doubleRotateDownFace();
            } else {
                cube.rotateDownFaceCounterclockwise();
            }
        }

        cube.doubleVerticalCubeTurn();
        cube.turnCubeLeft();

        while(!(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && frontFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(FRONT_FACE_COLOR)
                && rightFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(RIGHT_FACE_COLOR))) {
            orientUpFaceCorner();
        }

        cube.turnCubeRight();
        cube.doubleVerticalCubeTurn();
    }

    private void orientUpFaceCorner() {
        cube.rotateRightFaceCounterclockwise();
        cube.rotateFrontFaceClockwise();
        cube.rotateRightFaceClockwise();
        cube.rotateFrontFaceCounterclockwise();
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

    private void moveAllWhiteEdgeSquaresFromBottomLayerToUpFace() {
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
        boolean leftSquarePositioned = upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR);
        boolean rightSquarePositioned = upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR);
        boolean topSquarePositioned = upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR);
        boolean bottomSquarePositioned = upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR);


        if (frontFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromFrontFaceMiddleRowLeftColumnToUpFace(leftSquarePositioned);
        }
        if (frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromFrontFaceMiddleRowRightColumnToUpFace(rightSquarePositioned);
        }
        if (leftFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromLeftFaceMiddleRowLeftColumnToUpFace(topSquarePositioned);
        }
        if (leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromLeftFaceMiddleRowRightColumnToUpFace(bottomSquarePositioned);
        }
        if (rightFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromRightFaceMiddleRowLeftColumnToUpFace(bottomSquarePositioned);
        }
        if (rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromRightFaceMiddleRowRightColumnToUpFace(topSquarePositioned);
        }
        if (backFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromBackFaceMiddleRowLeftColumnToUpFace(rightSquarePositioned);
        }
        if (backFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            moveWhiteSquareFromBackFaceMiddleRowRightColumnToUpFace(leftSquarePositioned);
        }
    }

    private void moveWhiteEdgeSquaresFromTopLayerToUpFace() {
        if (leftFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            cube.doubleRotateLeftFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(leftFace);
        }
        if (frontFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            cube.doubleRotateFrontFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(frontFace);
        }
        if (rightFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            cube.doubleRotateRightFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(rightFace);
        }
        if (backFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)) {
            cube.doubleRotateBackFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(backFace);
        }
    }

    private void orientUpFaceWhiteEdges() {
        if (upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)
                && !backFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(BACK_FACE_COLOR)) {
            cube.doubleRotateBackFace();
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
        if (upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && !leftFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(LEFT_FACE_COLOR)) {
            cube.doubleRotateLeftFace();
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && !rightFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(RIGHT_FACE_COLOR)) {
            cube.doubleRotateRightFace();
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)
                && !frontFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(FRONT_FACE_COLOR)) {
            cube.doubleRotateFrontFace();
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteSquareFromFrontFaceMiddleRowLeftColumnToUpFace(boolean leftSquarePositioned) {
        cube.rotateLeftFaceClockwise();
        cube.rotateDownFaceClockwise();
        if (leftSquarePositioned) {
            cube.rotateLeftFaceCounterclockwise();
        }
        moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
    }

    private void moveWhiteSquareFromFrontFaceMiddleRowRightColumnToUpFace(boolean rightSquarePositioned) {
        cube.rotateRightFaceCounterclockwise();
        cube.rotateDownFaceClockwise();
        if (rightSquarePositioned) {
            cube.rotateRightFaceClockwise();
        }
        moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
    }

    private void moveWhiteSquareFromLeftFaceMiddleRowLeftColumnToUpFace(boolean topSquarePositioned) {
        cube.rotateBackFaceClockwise();
        cube.rotateDownFaceClockwise();
        if (topSquarePositioned) {
            cube.rotateBackFaceCounterclockwise();
        }
        moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
    }

    private void moveWhiteSquareFromLeftFaceMiddleRowRightColumnToUpFace(boolean bottomSquarePositioned) {
        cube.rotateFrontFaceCounterclockwise();
        cube.rotateDownFaceClockwise();
        if (bottomSquarePositioned) {
            cube.rotateFrontFaceClockwise();
        }
        moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
    }

    private void moveWhiteSquareFromRightFaceMiddleRowLeftColumnToUpFace(boolean bottomSquarePositioned) {
        cube.rotateFrontFaceClockwise();
        cube.rotateDownFaceClockwise();
        if (bottomSquarePositioned) {
            cube.rotateFrontFaceCounterclockwise();
        }
        moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
    }

    private void moveWhiteSquareFromRightFaceMiddleRowRightColumnToUpFace(boolean topSquarePositioned) {
        cube.rotateBackFaceCounterclockwise();
        cube.rotateDownFaceClockwise();
        if (topSquarePositioned) {
            cube.rotateBackFaceClockwise();
        }
        moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
    }

    private void moveWhiteSquareFromBackFaceMiddleRowLeftColumnToUpFace(boolean rightSquarePositioned) {
        cube.rotateRightFaceClockwise();
        cube.rotateDownFaceClockwise();
        if (rightSquarePositioned) {
            cube.rotateRightFaceCounterclockwise();
        }
        moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
    }

    private void moveWhiteSquareFromBackFaceMiddleRowRightColumnToUpFace(boolean leftSquarePositioned) {
        cube.rotateLeftFaceCounterclockwise();
        cube.rotateDownFaceClockwise();
        if (leftSquarePositioned) {
            cube.rotateLeftFaceClockwise();
        }
        moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
    }

    private void moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap.get(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            moveWhiteEdgeSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromLeftFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap
                .get(leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN])
                .getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            moveWhiteEdgeSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.rotateDownFaceClockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromBackFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap.get(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            moveWhiteEdgeSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.doubleRotateDownFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteSquareFromRightFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = adjacentEdgesMap.get(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

        if (adjacentColor.equals(LEFT_FACE_COLOR)) {
            cube.doubleRotateDownFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(leftFace);
        }
        else if (adjacentColor.equals(RIGHT_FACE_COLOR)) {
            moveWhiteEdgeSquareFromBottomLayerToUpFace(rightFace);
        }
        else if (adjacentColor.equals(BACK_FACE_COLOR)) {
            cube.rotateDownFaceClockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(backFace);
        }
        else {
            cube.rotateDownFaceCounterclockwise();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(frontFace);
        }
    }

    private void moveWhiteEdgeSquareFromBottomLayerToUpFace(Face originalFace) {
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

    private void setAdjacentCornersMap() {
        adjacentCornersMap.put(downFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {frontFace.squares[BOTTOM_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(frontFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[TOP_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(leftFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {frontFace.squares[BOTTOM_ROW][LEFT_COLUMN], downFace.squares[TOP_ROW][LEFT_COLUMN]});

        adjacentCornersMap.put(downFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {downFace.squares[TOP_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(rightFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[TOP_ROW][RIGHT_COLUMN], frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});

        adjacentCornersMap.put(downFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {leftFace.squares[BOTTOM_ROW][LEFT_COLUMN], backFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(leftFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][LEFT_COLUMN], backFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(backFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][LEFT_COLUMN]});

        adjacentCornersMap.put(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {rightFace.squares[BOTTOM_ROW][RIGHT_COLUMN], backFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(rightFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][RIGHT_COLUMN], backFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(backFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});

        adjacentCornersMap.put(upFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {leftFace.squares[TOP_ROW][LEFT_COLUMN], backFace.squares[TOP_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(leftFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][LEFT_COLUMN], backFace.squares[TOP_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(backFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][LEFT_COLUMN], leftFace.squares[TOP_ROW][LEFT_COLUMN]});

        adjacentCornersMap.put(upFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {rightFace.squares[TOP_ROW][RIGHT_COLUMN], backFace.squares[TOP_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(rightFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][RIGHT_COLUMN], backFace.squares[TOP_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(backFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][RIGHT_COLUMN], rightFace.squares[TOP_ROW][RIGHT_COLUMN]});

        adjacentCornersMap.put(upFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {leftFace.squares[TOP_ROW][RIGHT_COLUMN], frontFace.squares[TOP_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(leftFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][LEFT_COLUMN], frontFace.squares[TOP_ROW][LEFT_COLUMN]});
        adjacentCornersMap.put(frontFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][LEFT_COLUMN], leftFace.squares[TOP_ROW][RIGHT_COLUMN]});

        adjacentCornersMap.put(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {rightFace.squares[TOP_ROW][LEFT_COLUMN], frontFace.squares[TOP_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(rightFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][RIGHT_COLUMN], frontFace.squares[TOP_ROW][RIGHT_COLUMN]});
        adjacentCornersMap.put(frontFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][RIGHT_COLUMN], rightFace.squares[TOP_ROW][LEFT_COLUMN]});
    }
}
