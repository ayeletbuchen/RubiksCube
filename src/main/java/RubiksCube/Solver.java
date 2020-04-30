package RubiksCube;

import java.awt.*;
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
    private EdgesMap edgesMap;
    private CornersMap cornersMap;
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
        edgesMap = new EdgesMap(upFace, leftFace, frontFace, rightFace, backFace, downFace);
        cornersMap = new CornersMap(upFace, leftFace, frontFace, rightFace, backFace, downFace);
        computerMoveStack = new Stack<>();
        userMoveStack = new Stack<>();
        Move.setCounterMoves();
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
        while (!whiteEdgesAreOriented()) {
            orientUpFaceWhiteEdges();
            moveWhiteEdgeSquaresFromDownFaceToUpFace();
            moveAllWhiteEdgeSquaresFromBottomLayerToUpFace();
            moveWhiteEdgeSquaresFromMiddleLayerToUpFace();
            moveWhiteEdgeSquaresFromTopLayerToUpFace();
        }
    }

    private void putWhiteCornersInPlace() {
        Square downFaceTopLeftSquare = downFace.squares[TOP_ROW][LEFT_COLUMN];
        Square downFaceTopRightSquare = downFace.squares[TOP_ROW][RIGHT_COLUMN];
        Square downFaceBottomLeftSquare = downFace.squares[BOTTOM_ROW][LEFT_COLUMN];
        Square downFaceBottomRightSquare = downFace.squares[BOTTOM_ROW][RIGHT_COLUMN];
        Square[] downFaceTopLeftCorner = cornersMap.get(downFaceTopLeftSquare);
        Square[] downFaceTopRightCorner = cornersMap.get(downFaceTopRightSquare);
        Square[] downFaceBottomLeftCorner = cornersMap.get(downFaceBottomLeftSquare);
        Square[] downFaceBottomRightCorner = cornersMap.get(downFaceBottomRightSquare);

        while(!whiteCornersAreOriented()) {
            System.out.println("putWhiteCornersInPlace()");

            // TODO orient white corner pieces that are on white layer but in wrong position

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
        System.out.println("orientWhiteCornerFromDownFace");
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
                orientUpFaceTopLeftCorner(downFaceRow, downFaceCol);
            } else {
                orientUpFaceTopRightCorner(downFaceRow, downFaceCol);
            }
        }
    }

    private void orientUpFaceBottomLeftCorner(int downFaceRow, int downFaceCol) {
        System.out.println("orientUpFaceBottomLeftCorner");
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

        cube.turnCubeRight();
        orientUpFaceBottomRightCorner(LEFT_FACE_COLOR, FRONT_FACE_COLOR);
        cube.turnCubeLeft();
    }

    private void orientUpFaceBottomRightCorner(int downFaceRow, int downFaceCol) {
        System.out.println("orientUpFaceBottomRightCorner");
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

        orientUpFaceBottomRightCorner(FRONT_FACE_COLOR, RIGHT_FACE_COLOR);
    }

    private void orientUpFaceTopLeftCorner(int downFaceRow, int downFaceCol) {
        System.out.println("orientUpFaceTopLeftCorner");
        if (downFaceRow == TOP_ROW) {
            if (downFaceCol == LEFT_COLUMN) {
                cube.rotateDownFaceCounterclockwise();
            } else {
                cube.doubleRotateDownFace();
            }
        } else if (downFaceRow == BOTTOM_ROW && downFaceCol == RIGHT_COLUMN) {
            cube.rotateDownFaceClockwise();
        }

        cube.doubleHorizontalCubeTurn();
        orientUpFaceBottomRightCorner(BACK_FACE_COLOR, LEFT_FACE_COLOR);
        cube.doubleHorizontalCubeTurn();
    }

    private void orientUpFaceTopRightCorner(int downFaceRow, int downFaceCol) {
        System.out.println("orientUpFaceTopRightCorner");
        if (downFaceRow == TOP_ROW) {
            if (downFaceCol == LEFT_COLUMN) {
                cube.doubleRotateDownFace();
            } else {
                cube.rotateDownFaceClockwise();
            }
        } else if (downFaceRow == BOTTOM_ROW && downFaceCol == LEFT_COLUMN) {
            cube.rotateDownFaceCounterclockwise();
        }

        cube.turnCubeLeft();
        orientUpFaceBottomRightCorner(RIGHT_FACE_COLOR, BACK_FACE_COLOR);
        cube.turnCubeRight();
    }

    private void orientUpFaceBottomRightCorner(Color frontFaceColor, Color rightFaceColor) {
        while(!(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && frontFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(frontFaceColor)
                && rightFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(rightFaceColor))) {
            System.out.println("orientUpFaceBottomRightCorner");
            cube.rotateRightFaceCounterclockwise();
            cube.rotateDownFaceCounterclockwise();
            cube.rotateRightFaceClockwise();
            cube.rotateDownFaceClockwise();
        }
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
        Color adjacentColor = edgesMap.get(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

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
        Color adjacentColor = edgesMap
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
        Color adjacentColor = edgesMap.get(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

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
        Color adjacentColor = edgesMap.get(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

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
        Color adjacentEdgeColor = edgesMap.get(downFace.squares[TOP_ROW][MIDDLE_COLUMN]).getColor();

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
        Color adjacentEdgeColor = edgesMap.get(downFace.squares[MIDDLE_ROW][LEFT_COLUMN]).getColor();

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
        Color adjacentEdgeColor = edgesMap.get(downFace.squares[MIDDLE_ROW][RIGHT_COLUMN]).getColor();

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
        Color adjacentEdgeColor = edgesMap.get(downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]).getColor();

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

    private boolean whiteEdgesAreOriented() {
        return upFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)
                && upFace.squares[MIDDLE_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && upFace.squares[MIDDLE_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor().equals(UP_FACE_COLOR)
                && leftFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(LEFT_FACE_COLOR)
                && frontFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(FRONT_FACE_COLOR)
                && rightFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(RIGHT_FACE_COLOR)
                && backFace.squares[TOP_ROW][MIDDLE_COLUMN].getColor().equals(BACK_FACE_COLOR);
    }

    private boolean whiteCornersAreOriented() {
        return upFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && leftFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(LEFT_FACE_COLOR)
                && backFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(BACK_FACE_COLOR)

                && upFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && rightFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(RIGHT_FACE_COLOR)
                && backFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(BACK_FACE_COLOR)

                && upFace.squares[BOTTOM_ROW][LEFT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && frontFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(FRONT_FACE_COLOR)
                && leftFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(LEFT_FACE_COLOR)

                && upFace.squares[BOTTOM_ROW][RIGHT_COLUMN].getColor().equals(UP_FACE_COLOR)
                && frontFace.squares[TOP_ROW][RIGHT_COLUMN].getColor().equals(FRONT_FACE_COLOR)
                && rightFace.squares[TOP_ROW][LEFT_COLUMN].getColor().equals(RIGHT_FACE_COLOR);
    }
}
