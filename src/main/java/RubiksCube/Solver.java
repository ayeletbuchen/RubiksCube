package RubiksCube;

import java.awt.*;
import java.util.Stack;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Solver extends Stack<Move> implements Observer<Move> {

    //<editor-fold defaultstate="collapsed" desc="Attributes">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Observer methods">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Solve">
    public void solve() {
        solveTopLayer();
        solveMiddleLayer();
        solveBottomLayer();
    }

    //<editor-fold defaultstate-"collapsed" desc="Solve top layer">
    private void solveTopLayer() {
        positionWhiteCenterSquare();
        createWhiteCross();
        putWhiteCornersInPlace();
    }

    //<editor-fold defaultstate="collapsed" desc="Position white center square">
    private void positionWhiteCenterSquare() {
        if (!isOriginalColor(upFace.squares[MIDDLE_ROW][MIDDLE_COLUMN])) {
            if (squareIsColor(leftFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
                cube.turnCubeClockwiseAlongZAxis();
            } else if (squareIsColor(frontFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
                cube.turnCubeUp();
            } else if (squareIsColor(rightFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
                cube.turnCubeCounterclockwiseAlongZAxis();
            } else if (squareIsColor(backFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
                cube.turnCubeDown();
            } else {
                cube.doubleVerticalCubeTurn();
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Create white cross">
    private void createWhiteCross() {
        while (!whiteEdgesAreOriented()) {
            orientUpFaceWhiteEdges();
            moveWhiteEdgeSquaresFromDownFaceToUpFace();
            moveAllWhiteEdgeSquaresFromBottomLayerToUpFace();
            moveWhiteEdgeSquaresFromMiddleLayerToUpFace();
            moveWhiteEdgeSquaresFromTopLayerToUpFace();
        }
    }

    private boolean whiteEdgesAreOriented() {
        return crossExists(UP_FACE_COLOR)
                && isOriginalColor(leftFace.squares[TOP_ROW][MIDDLE_COLUMN])
                && isOriginalColor(frontFace.squares[TOP_ROW][MIDDLE_COLUMN])
                && isOriginalColor(rightFace.squares[TOP_ROW][MIDDLE_COLUMN])
                && isOriginalColor(backFace.squares[TOP_ROW][MIDDLE_COLUMN]);
    }

    //<editor-fold desc="Orient up face white edges">
    private void orientUpFaceWhiteEdges() {
        if (isOriginalColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN])
                && !isOriginalColor(backFace.squares[TOP_ROW][MIDDLE_COLUMN])) {
            cube.doubleRotateBackFace();
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
        if (isOriginalColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN])
                && !isOriginalColor(leftFace.squares[TOP_ROW][MIDDLE_COLUMN])) {
            cube.doubleRotateLeftFace();
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (isOriginalColor(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN])
                && !isOriginalColor(rightFace.squares[TOP_ROW][MIDDLE_COLUMN])) {
            cube.doubleRotateRightFace();
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (isOriginalColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN])
                && !isOriginalColor(frontFace.squares[TOP_ROW][MIDDLE_COLUMN])) {
            cube.doubleRotateFrontFace();
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Move white edge squares from down face">
    private void moveWhiteEdgeSquaresFromDownFaceToUpFace() {
        if (squareIsColor(downFace.squares[TOP_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace();
        }
        if (squareIsColor(downFace.squares[MIDDLE_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceMiddleRowLeftColumnToUpFace();
        }
        if (squareIsColor(downFace.squares[MIDDLE_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceMiddleRowRightColumnToUpFace();
        }
        if (squareIsColor(downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromDownFaceBottomRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteSquareFromDownFaceTopRowMiddleColumnToUpFace() {
        Color adjacentEdgeColor = getAdjacentEdgeColor(downFace.squares[TOP_ROW][MIDDLE_COLUMN]);

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
        Color adjacentEdgeColor = getAdjacentEdgeColor(downFace.squares[MIDDLE_ROW][LEFT_COLUMN]);

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
        Color adjacentEdgeColor = getAdjacentEdgeColor(downFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);

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
        Color adjacentEdgeColor = getAdjacentEdgeColor(downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);

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
    //</editor-fold>

    //<editor-fold desc="Move white edge square from bottom layer">
    private void moveAllWhiteEdgeSquaresFromBottomLayerToUpFace() {
        if (squareIsColor(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace();
        }
        if (squareIsColor(leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromLeftFaceBottomRowMiddleColumnToUpFace();
        }
        if (squareIsColor(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromBackFaceBottomRowMiddleColumnToUpFace();
        }
        if (squareIsColor(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromRightFaceBottomRowMiddleColumnToUpFace();
        }
    }

    private void moveWhiteSquareFromFrontFaceBottomRowMiddleColumnToUpFace() {
        Color adjacentColor = getAdjacentEdgeColor(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);

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
        Color adjacentColor = getAdjacentEdgeColor(leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);

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
        Color adjacentColor = getAdjacentEdgeColor(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);

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
        Color adjacentColor = getAdjacentEdgeColor(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);

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
    //</editor-fold>

    //<editor-fold desc="Move white edge squares from middle layer">
    private void moveWhiteEdgeSquaresFromMiddleLayerToUpFace() {
        boolean leftSquarePositioned = isOriginalColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        boolean rightSquarePositioned = isOriginalColor(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        boolean topSquarePositioned = isOriginalColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        boolean bottomSquarePositioned = isOriginalColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);

        if (squareIsColor(frontFace.squares[MIDDLE_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromFrontFaceMiddleRowLeftColumnToUpFace(leftSquarePositioned);
        }
        if (squareIsColor(frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromFrontFaceMiddleRowRightColumnToUpFace(rightSquarePositioned);
        }
        if (squareIsColor(leftFace.squares[MIDDLE_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromLeftFaceMiddleRowLeftColumnToUpFace(topSquarePositioned);
        }
        if (squareIsColor(leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromLeftFaceMiddleRowRightColumnToUpFace(bottomSquarePositioned);
        }
        if (squareIsColor(rightFace.squares[MIDDLE_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromRightFaceMiddleRowLeftColumnToUpFace(bottomSquarePositioned);
        }
        if (squareIsColor(rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromRightFaceMiddleRowRightColumnToUpFace(topSquarePositioned);
        }
        if (squareIsColor(backFace.squares[MIDDLE_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromBackFaceMiddleRowLeftColumnToUpFace(rightSquarePositioned);
        }
        if (squareIsColor(backFace.squares[MIDDLE_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            moveWhiteSquareFromBackFaceMiddleRowRightColumnToUpFace(leftSquarePositioned);
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
    //</editor-fold>

    //<editor-fold desc="Move white edge squares from top layer">
    private void moveWhiteEdgeSquaresFromTopLayerToUpFace() {
        if (squareIsColor(leftFace.squares[TOP_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            cube.doubleRotateLeftFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(leftFace);
        }
        if (squareIsColor(frontFace.squares[TOP_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            cube.doubleRotateFrontFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(frontFace);
        }
        if (squareIsColor(rightFace.squares[TOP_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            cube.doubleRotateRightFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(rightFace);
        }
        if (squareIsColor(backFace.squares[TOP_ROW][MIDDLE_COLUMN], UP_FACE_COLOR)) {
            cube.doubleRotateBackFace();
            moveWhiteEdgeSquareFromBottomLayerToUpFace(backFace);
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Put white corners in place">
    private void putWhiteCornersInPlace() {
        while(!whiteCornersAreOriented()) {
            moveWhiteCornersInWrongPosition();
            moveWhiteCornersFromDownFace();
        }
    }

    private boolean whiteCornersAreOriented() {
        return isOriginalColor(upFace.squares[TOP_ROW][LEFT_COLUMN])
                && isOriginalColor(leftFace.squares[TOP_ROW][LEFT_COLUMN])
                && isOriginalColor(backFace.squares[TOP_ROW][RIGHT_COLUMN])

                && isOriginalColor(upFace.squares[TOP_ROW][RIGHT_COLUMN])
                && isOriginalColor(rightFace.squares[TOP_ROW][RIGHT_COLUMN])
                && isOriginalColor(backFace.squares[TOP_ROW][LEFT_COLUMN])

                && isOriginalColor(upFace.squares[BOTTOM_ROW][LEFT_COLUMN])
                && isOriginalColor(frontFace.squares[TOP_ROW][LEFT_COLUMN])
                && isOriginalColor(leftFace.squares[TOP_ROW][RIGHT_COLUMN])

                && isOriginalColor(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN])
                && isOriginalColor(frontFace.squares[TOP_ROW][RIGHT_COLUMN])
                && isOriginalColor(rightFace.squares[TOP_ROW][LEFT_COLUMN]);
    }

    //<editor-fold desc="Move white corners in wrong position">
    private void moveWhiteCornersInWrongPosition() {
        fixUpFaceCorner(upFace.squares[TOP_ROW][LEFT_COLUMN]);
        fixUpFaceCorner(upFace.squares[TOP_ROW][RIGHT_COLUMN]);
        fixUpFaceCorner(upFace.squares[BOTTOM_ROW][LEFT_COLUMN]);
        fixUpFaceCorner(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN]);
    }

    private void fixUpFaceCorner(Square square) {
        Square[] corner = cornersMap.get(square);

        if (cornerHasColor(square, UP_FACE_COLOR)) {
            if (cornerInCorrectPosition(square, square.getORIGINAL_COLOR(),
                    corner[0].getORIGINAL_COLOR(), corner[1].getORIGINAL_COLOR())) {
                if (!cornerInCorrectOrientation(square)) {
                    reorientUpFaceCorner(square);
                }
            } else {
                repositionUpFaceCorner(square);
            }
        }
    }

    private void reorientUpFaceCorner(Square square) {
        if (square.equals(upFace.squares[TOP_ROW][LEFT_COLUMN])) {
            cube.doubleHorizontalCubeTurn();
            orientUpFaceCorner(BACK_FACE_COLOR, LEFT_FACE_COLOR);
            cube.doubleHorizontalCubeTurn();
        } else if (square.equals(upFace.squares[TOP_ROW][RIGHT_COLUMN])) {
            cube.turnCubeLeft();
            orientUpFaceCorner(RIGHT_FACE_COLOR, BACK_FACE_COLOR);
            cube.turnCubeRight();
        } else if (square.equals(upFace.squares[BOTTOM_ROW][LEFT_COLUMN])) {
            cube.turnCubeRight();
            orientUpFaceCorner(LEFT_FACE_COLOR, FRONT_FACE_COLOR);
            cube.turnCubeLeft();
        } else if (square.equals(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN])) {
            orientUpFaceCorner(FRONT_FACE_COLOR, RIGHT_FACE_COLOR);
        }
    }

    private void repositionUpFaceCorner(Square square) {
        if (square.equals(upFace.squares[TOP_ROW][LEFT_COLUMN])) {
            cube.rotateBackFaceClockwise();
            cube.rotateDownFaceClockwise();
            cube.rotateBackFaceCounterclockwise();
            orientWhiteCornerFromDownFace(downFace.squares[TOP_ROW][LEFT_COLUMN]);
        } else if (square.equals(upFace.squares[TOP_ROW][RIGHT_COLUMN])) {
            cube.rotateBackFaceCounterclockwise();
            cube.rotateDownFaceCounterclockwise();
            cube.rotateBackFaceClockwise();
            orientWhiteCornerFromDownFace(downFace.squares[TOP_ROW][RIGHT_COLUMN]);
        } else if (square.equals(upFace.squares[BOTTOM_ROW][LEFT_COLUMN])) {
            cube.rotateLeftFaceClockwise();
            cube.rotateDownFaceClockwise();
            cube.rotateLeftFaceCounterclockwise();
            orientWhiteCornerFromDownFace(downFace.squares[TOP_ROW][RIGHT_COLUMN]);
        } else if (square.equals(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN])) {
            cube.rotateRightFaceCounterclockwise();
            cube.rotateDownFaceCounterclockwise();
            cube.rotateRightFaceClockwise();
            orientWhiteCornerFromDownFace(downFace.squares[TOP_ROW][LEFT_COLUMN]);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Move white corners from down face">
    private void moveWhiteCornersFromDownFace() {
        if (cornerHasColor(downFace.squares[TOP_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            orientWhiteCornerFromDownFace(downFace.squares[TOP_ROW][LEFT_COLUMN]);
        }
        if (cornerHasColor(downFace.squares[TOP_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            orientWhiteCornerFromDownFace(downFace.squares[TOP_ROW][RIGHT_COLUMN]);
        }
        if (cornerHasColor(downFace.squares[BOTTOM_ROW][LEFT_COLUMN], UP_FACE_COLOR)) {
            orientWhiteCornerFromDownFace(downFace.squares[BOTTOM_ROW][LEFT_COLUMN]);
        }
        if (cornerHasColor(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN], UP_FACE_COLOR)) {
            orientWhiteCornerFromDownFace(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN]);
        }
    }

    private void orientWhiteCornerFromDownFace(Square square) {
        if (cornerHasColor(square, FRONT_FACE_COLOR)) {
            if (cornerHasColor(square, LEFT_FACE_COLOR)) {
                orientUpFaceBottomLeftCorner(square);
            } else {
                orientUpFaceBottomRightCorner(square);
            }
        } else if (cornerHasColor(square, BACK_FACE_COLOR)) {
            if (cornerHasColor(square, LEFT_FACE_COLOR)) {
                orientUpFaceTopLeftCorner(square);
            } else {
                orientUpFaceTopRightCorner(square);
            }
        }
    }

    private void orientUpFaceBottomLeftCorner(Square square) {
        if (square.equals(downFace.squares[TOP_ROW][RIGHT_COLUMN])) {
            cube.rotateDownFaceCounterclockwise();
        } else if (square.equals(downFace.squares[BOTTOM_ROW][LEFT_COLUMN])) {
            cube.rotateDownFaceClockwise();
        } else if (square.equals(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN])) {
            cube.doubleRotateDownFace();
        }

        cube.turnCubeRight();
        orientUpFaceCorner(LEFT_FACE_COLOR, FRONT_FACE_COLOR);
        cube.turnCubeLeft();
    }

    private void orientUpFaceBottomRightCorner(Square square) {
        if (square.equals(downFace.squares[TOP_ROW][LEFT_COLUMN])) {
            cube.rotateDownFaceClockwise();
        } else if (square.equals(downFace.squares[BOTTOM_ROW][LEFT_COLUMN])) {
            cube.doubleRotateDownFace();
        } else if (square.equals(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN])) {
            cube.rotateDownFaceCounterclockwise();
        }

        orientUpFaceCorner(FRONT_FACE_COLOR, RIGHT_FACE_COLOR);
    }

    private void orientUpFaceTopLeftCorner(Square square) {
        if (square.equals(downFace.squares[TOP_ROW][LEFT_COLUMN])) {
            cube.rotateDownFaceCounterclockwise();
        } else if (square.equals(downFace.squares[TOP_ROW][RIGHT_COLUMN])) {
            cube.doubleRotateDownFace();
        } else if (square.equals(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN])) {
            cube.rotateDownFaceClockwise();
        }

        cube.doubleHorizontalCubeTurn();
        orientUpFaceCorner(BACK_FACE_COLOR, LEFT_FACE_COLOR);
        cube.doubleHorizontalCubeTurn();
    }

    private void orientUpFaceTopRightCorner(Square square) {
        if (square.equals(downFace.squares[TOP_ROW][LEFT_COLUMN])) {
            cube.doubleRotateDownFace();
        } else if (square.equals(downFace.squares[TOP_ROW][RIGHT_COLUMN])) {
            cube.rotateDownFaceClockwise();
        } else if (square.equals(downFace.squares[BOTTOM_ROW][LEFT_COLUMN])) {
            cube.rotateDownFaceCounterclockwise();
        }

        cube.turnCubeLeft();
        orientUpFaceCorner(RIGHT_FACE_COLOR, BACK_FACE_COLOR);
        cube.turnCubeRight();
    }

    private void orientUpFaceCorner(Color frontFaceColor, Color rightFaceColor) {
        while(!upFaceBottomRightCornerIsOriented(frontFaceColor, rightFaceColor)) {
            cube.rotateRightFaceCounterclockwise();
            cube.rotateDownFaceCounterclockwise();
            cube.rotateRightFaceClockwise();
            cube.rotateDownFaceClockwise();
        }
    }

    private boolean upFaceBottomRightCornerIsOriented(Color frontFaceColor, Color rightFaceColor) {
        return isOriginalColor(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN])
                && squareIsColor(frontFace.squares[TOP_ROW][RIGHT_COLUMN], frontFaceColor)
                && squareIsColor(rightFace.squares[TOP_ROW][LEFT_COLUMN], rightFaceColor);
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Solve middle layer">
    private void solveMiddleLayer() {
        orientMiddleLayerCenterSquares();
        cube.doubleVerticalCubeTurn();
        moveSquaresFromTopLayerToMiddleLayer();
        moveMiddleLayerSquaresInWrongPosition();
    }

    //<editor-fold defaultstate="collapsed" desc="Orient middle layer center squares">
    private void orientMiddleLayerCenterSquares() {
        if (!isOriginalColor(frontFace.squares[MIDDLE_ROW][MIDDLE_COLUMN])) {
            if (squareIsColor(leftFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], FRONT_FACE_COLOR)) {
                cube.sliceEquatorialLayerClockwise();
            } else if (squareIsColor(rightFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], FRONT_FACE_COLOR)) {
                cube.sliceEquatorialLayerCounterclockwise();
            } else {
                cube.doubleSliceEquatorialLayer();
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Move squares from top layer to middle layer">
    private void moveSquaresFromTopLayerToMiddleLayer() {
        Square square = frontFace.squares[TOP_ROW][MIDDLE_COLUMN];

        while (middleLayerSquaresOnTopLayer()) {
            if (!edgesHaveColor(square, DOWN_FACE_COLOR)) {
                if (squareIsColor(square, LEFT_FACE_COLOR)) {
                    cube.rotateUpFaceClockwise();
                    cube.turnCubeRight();
                    if (edgeIsColor(square, FRONT_FACE_COLOR)) {
                        middleLayerLeftAlgorithm();
                    } else if (edgeIsColor(square, BACK_FACE_COLOR)) {
                        middleLayerRightAlgorithm();
                    }
                    cube.turnCubeLeft();
                } else if (squareIsColor(square, BACK_FACE_COLOR)) {
                    if (edgeIsColor(square, LEFT_FACE_COLOR)) {
                        middleLayerLeftAlgorithm();
                    } else if (edgeIsColor(square, RIGHT_FACE_COLOR)) {
                        middleLayerRightAlgorithm();
                    }
                } else if (squareIsColor(square, RIGHT_FACE_COLOR)) {
                    cube.rotateUpFaceCounterclockwise();
                    cube.turnCubeLeft();
                    if (edgeIsColor(square, BACK_FACE_COLOR)) {
                        middleLayerLeftAlgorithm();
                    } else if (edgeIsColor(square, FRONT_FACE_COLOR)) {
                        middleLayerRightAlgorithm();
                    }
                    cube.turnCubeRight();
                } else if (squareIsColor(square, FRONT_FACE_COLOR)) {
                    cube.doubleRotateUpFace();
                    cube.doubleHorizontalCubeTurn();
                    if (edgeIsColor(square, RIGHT_FACE_COLOR)) {
                        middleLayerLeftAlgorithm();
                    } else if (edgeIsColor(square, LEFT_FACE_COLOR)) {
                        middleLayerRightAlgorithm();
                    }
                    cube.doubleHorizontalCubeTurn();
                }
            }
            if (edgesHaveColor(square, DOWN_FACE_COLOR)) {
                cube.rotateUpFaceClockwise();
            }
        }
    }

    private boolean middleLayerSquaresOnTopLayer() {
        return !edgesHaveColor(frontFace.squares[TOP_ROW][MIDDLE_COLUMN], DOWN_FACE_COLOR)
                || !edgesHaveColor(leftFace.squares[TOP_ROW][MIDDLE_COLUMN], DOWN_FACE_COLOR)
                || !edgesHaveColor(rightFace.squares[TOP_ROW][MIDDLE_COLUMN], DOWN_FACE_COLOR)
                || !edgesHaveColor(backFace.squares[TOP_ROW][MIDDLE_COLUMN], DOWN_FACE_COLOR);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Move middle layer squares in wrong position">
    private void moveMiddleLayerSquaresInWrongPosition() {
        while (!middleLayerIsOriented()) {
            if (!squareIsColor(frontFace.squares[MIDDLE_ROW][LEFT_COLUMN], BACK_FACE_COLOR)
                    || !edgeIsOriginalColor(frontFace.squares[MIDDLE_ROW][LEFT_COLUMN])) {
                middleLayerLeftAlgorithm();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!squareIsColor(frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN], BACK_FACE_COLOR)
                    || !edgeIsOriginalColor(frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN])) {
                middleLayerRightAlgorithm();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!isOriginalColor(leftFace.squares[MIDDLE_ROW][LEFT_COLUMN])
                    || !edgeIsColor(leftFace.squares[MIDDLE_ROW][LEFT_COLUMN], FRONT_FACE_COLOR)) {
                cube.turnCubeRight();
                middleLayerLeftAlgorithm();
                cube.turnCubeLeft();
                cube.rotateUpFaceCounterclockwise();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!isOriginalColor(leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN])
                    || !edgeIsColor(leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN], BACK_FACE_COLOR)) {
                cube.turnCubeRight();
                middleLayerRightAlgorithm();
                cube.turnCubeLeft();
                cube.rotateUpFaceCounterclockwise();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!isOriginalColor(rightFace.squares[MIDDLE_ROW][LEFT_COLUMN])
                    || !edgeIsColor(rightFace.squares[MIDDLE_ROW][LEFT_COLUMN], BACK_FACE_COLOR)) {
                cube.turnCubeLeft();
                middleLayerLeftAlgorithm();
                cube.turnCubeRight();
                cube.rotateUpFaceClockwise();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!isOriginalColor(rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN])
                    || !edgeIsColor(rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN], FRONT_FACE_COLOR)) {
                cube.turnCubeLeft();
                middleLayerRightAlgorithm();
                cube.turnCubeRight();
                cube.rotateUpFaceClockwise();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!squareIsColor(backFace.squares[MIDDLE_ROW][LEFT_COLUMN], FRONT_FACE_COLOR)
                    || !edgeIsOriginalColor(backFace.squares[MIDDLE_ROW][LEFT_COLUMN])) {
                cube.doubleHorizontalCubeTurn();
                middleLayerLeftAlgorithm();
                cube.doubleHorizontalCubeTurn();
                cube.doubleRotateUpFace();
                moveSquaresFromTopLayerToMiddleLayer();
            }
            if (!squareIsColor(backFace.squares[MIDDLE_ROW][RIGHT_COLUMN], FRONT_FACE_COLOR)
                    || !edgeIsOriginalColor(backFace.squares[MIDDLE_ROW][RIGHT_COLUMN])) {
                cube.doubleHorizontalCubeTurn();
                middleLayerRightAlgorithm();
                cube.doubleHorizontalCubeTurn();
                cube.doubleRotateUpFace();
                moveSquaresFromTopLayerToMiddleLayer();
            }
        }
    }

    private boolean middleLayerIsOriented() {
        return (squareIsColor(frontFace.squares[MIDDLE_ROW][LEFT_COLUMN], BACK_FACE_COLOR)
                && squareIsColor(frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN], BACK_FACE_COLOR)

                && isOriginalColor(leftFace.squares[MIDDLE_ROW][LEFT_COLUMN])
                && isOriginalColor(leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN])

                && isOriginalColor(rightFace.squares[MIDDLE_ROW][LEFT_COLUMN])
                && isOriginalColor(rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN])

                && squareIsColor(backFace.squares[MIDDLE_ROW][LEFT_COLUMN], FRONT_FACE_COLOR)
                && squareIsColor(backFace.squares[MIDDLE_ROW][RIGHT_COLUMN], FRONT_FACE_COLOR)
        );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Middle layer algorithms">
    private void  middleLayerRightAlgorithm() {
        cube.rotateUpFaceClockwise();
        cube.rotateRightFaceClockwise();
        cube.rotateUpFaceCounterclockwise();
        cube.rotateRightFaceCounterclockwise();
        cube.rotateUpFaceCounterclockwise();
        cube.rotateFrontFaceCounterclockwise();
        cube.rotateUpFaceClockwise();
        cube.rotateFrontFaceClockwise();
    }

    private void middleLayerLeftAlgorithm() {
        cube.rotateUpFaceCounterclockwise();
        cube.rotateLeftFaceCounterclockwise();
        cube.rotateUpFaceClockwise();
        cube.rotateLeftFaceClockwise();
        cube.rotateUpFaceClockwise();
        cube.rotateFrontFaceClockwise();
        cube.rotateUpFaceCounterclockwise();
        cube.rotateFrontFaceCounterclockwise();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Solve bottom layer">
    private void solveBottomLayer() {
        createBottomCross();
    }

    //<editor-fold defaultstate="collapsed" desc="Create bottom cross">
    private void createBottomCross() {
        if (!crossExists(DOWN_FACE_COLOR)) {
            orientBottomSquaresOnUpFace();
            while (!crossExists(DOWN_FACE_COLOR)) {
                bottomCrossAlgorithm();
            }
        }
    }

    private void orientBottomSquaresOnUpFace() {
        if (squareIsColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN], DOWN_FACE_COLOR)) {
            if (!squareIsColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], DOWN_FACE_COLOR)) {
                cube.rotateUpFaceCounterclockwise();
            }
        } else if (squareIsColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], DOWN_FACE_COLOR)) {
            if (squareIsColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], DOWN_FACE_COLOR)) {
                cube.rotateUpFaceClockwise();
            } else {
                cube.doubleRotateUpFace();
            }
        } else {
            bottomCrossAlgorithm();
            orientBottomSquaresOnUpFace();
        }
    }

    private void bottomCrossAlgorithm() {
        cube.rotateFrontFaceClockwise();
        cube.rotateUpFaceClockwise();
        cube.rotateRightFaceClockwise();
        cube.rotateUpFaceCounterclockwise();
        cube.rotateRightFaceCounterclockwise();
        cube.rotateFrontFaceCounterclockwise();
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Methods used throughout class">
    //<editor-fold defaultstate="collapsed" desc="Corner booleans">
    private boolean cornerInCorrectPosition(Square square, Color color1, Color color2, Color color3) {
        boolean foundColor1 = false;
        boolean foundColor2 = false;
        boolean foundColor3 = false;

        if (cornerHasColor(square, color1)) {
            foundColor1 = true;
        }
        if (cornerHasColor(square, color2)) {
            foundColor1 = true;
        }
        if (cornerHasColor(square, color3)) {
            foundColor3 = true;
        }

        return foundColor1 && foundColor2 && foundColor3;
    }

    private boolean cornerHasColor(Square square, Color color) {
        Square[] corner = cornersMap.get(square);

        return squareIsColor(square, color)
                || squareIsColor(corner[0], color)
                || squareIsColor(corner[1], color);
    }

    private boolean cornerInCorrectOrientation(Square square) {
        Square[] corner = cornersMap.get(square);

        return isOriginalColor(square)
                && isOriginalColor(corner[0])
                && isOriginalColor(corner[1]);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Square characteristics">
    private boolean isOriginalColor(Square square) {
        return squareIsColor(square, square.getORIGINAL_COLOR());
    }

    private boolean squareIsColor(Square square, Color color) {
        return square.getColor().equals(color);
    }

    private boolean edgesHaveColor(Square square, Color color) {
        return squareIsColor(square, color) || edgeIsColor(square, color);
    }

    private boolean edgeIsColor(Square square, Color color) {
        return getAdjacentEdgeColor(square).equals(color);
    }

    private boolean edgeIsOriginalColor(Square square) {
        return getAdjacentEdgeColor(square).equals(edgesMap.get(square).getORIGINAL_COLOR());
    }

    private Color getAdjacentEdgeColor(Square square) {
        return edgesMap.get(square).getColor();
    }

    private boolean edgesAreOriginalColors(Square square) {
        return isOriginalColor(square) && isOriginalColor(edgesMap.get(square));
    }
    //</editor-fold>

    private boolean crossExists(Color color) {
        return squareIsColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN], color)
                && squareIsColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], color)
                && squareIsColor(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN], color)
                && squareIsColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], color);
    }
    //</editor-fold>
}
