package RubiksCube;

import java.awt.*;
import java.util.Stack;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Solver extends Stack<Move> implements Observer<Move>, CubeValues, CubeColors {

    //<editor-fold defaultstate="collapsed" desc="Attributes">
    private Cube cube;
    private Face upFace , leftFace, frontFace, rightFace, backFace, downFace;
    private boolean computerSolving, reshuffling;
    private Stack<Move> computerMoveStack, solveStack;
    private EdgesMap edgesMap;
    private CornersMap cornersMap;
    private Color upColor, leftColor, frontColor, rightColor, backColor, downColor;
    private DirectionLabel directionLabel;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Solver(Cube cube, DirectionLabel directionLabel) {
        this.cube = cube;
        this.directionLabel = directionLabel;
        upFace = cube.getUpFace();
        leftFace = cube.getLeftFace();
        frontFace = cube.getFrontFace();
        rightFace = cube.getRightFace();
        backFace = cube.getBackFace();
        downFace = cube.getDownFace();
        upColor = UP_FACE_COLOR;
        leftColor = LEFT_FACE_COLOR;
        frontColor = FRONT_FACE_COLOR;
        rightColor = RIGHT_FACE_COLOR;
        backColor = BACK_FACE_COLOR;
        downColor = DOWN_FACE_COLOR;
        computerSolving = false;
        reshuffling = false;
        edgesMap = new EdgesMap(upFace, leftFace, frontFace, rightFace, backFace, downFace);
        cornersMap = new CornersMap(upFace, leftFace, frontFace, rightFace, backFace, downFace);
        solveStack = new Stack<>();
        computerMoveStack = new Stack<>();
        Move.setCounterMoves();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Observer methods">
    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(Move move) {
        cube.repaint();
        if (move.equals(Move.SHUFFLE)) {
            computerMoveStack.clear();
            solveStack.clear();
        }
        else if (computerSolving) {
            computerMoveStack.push(move);
        } else if (!reshuffling) {
            if (!solveStack.isEmpty()) {
                Move nextMove = solveStack.peek();
                if (move.equals(nextMove)) {
                    solveStack.pop();
                } else {
                    solveStack.push(move.getCounterMove());
                }
                if (solveStack.isEmpty()) {
                    directionLabel.setText("Good job!");
                } else {
                    directionLabel.setText(solveStack.peek().getPrompt());
                }
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
        computerSolving = true;
        solveTopLayer();
        solveMiddleLayer();
        //solveBottomLayer();
        computerSolving = false;
        reshuffleCube();
    }

    private void reshuffleCube() {
        reshuffling = true;

        while(!computerMoveStack.isEmpty()) {
            Move move = computerMoveStack.pop();
            cube.doMove(move.getCounterMove());
            solveStack.push(move);
        }
        if (!solveStack.isEmpty()) {
            directionLabel.setText(solveStack.peek().getPrompt());
        }
        reshuffling = false;
    }

    //<editor-fold defaultstate-"collapsed" desc="Solve top layer">
    private void solveTopLayer() {
        positionUpFaceCenter();
        createWhiteCross();
        positionUpCorners();
    }

    //<editor-fold defaultstate="collapsed" desc="Position white center square">
    private void positionUpFaceCenter() {
        if (!squareIsColor(upFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], upColor)) {
            if (squareIsColor(leftFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], upColor)) {
                cube.turnCubeClockwiseAlongZAxis();
            } else if (squareIsColor(frontFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], upColor)) {
                cube.turnCubeUp();
            } else if (squareIsColor(rightFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], upColor)) {
                cube.turnCubeCounterclockwiseAlongZAxis();
            } else if (squareIsColor(backFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], upColor)) {
                cube.turnCubeDown();
            } else {
                cube.doubleVerticalCubeTurn();
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Create white cross">
    private void createWhiteCross() {
        while (!upCrossExists()) {
            moveUpFaceEdgesFromBottomLayer();
            moveUpFaceEdgesFromMiddleLayer();
            moveUpFaceEdgesFromTopLayer();
        }
    }

    //<editor-fold desc="Move up face edges from bottom layer">
    private void moveUpFaceEdgesFromBottomLayer() {
        moveUpFaceEdgeFromBottomLayer(TOP_ROW, MIDDLE_COLUMN);
        moveUpFaceEdgeFromBottomLayer(MIDDLE_ROW, LEFT_COLUMN);
        moveUpFaceEdgeFromBottomLayer(MIDDLE_ROW, RIGHT_COLUMN);
        moveUpFaceEdgeFromBottomLayer(BOTTOM_ROW, MIDDLE_COLUMN);
    }

    private void moveUpFaceEdgeFromBottomLayer(int startRow, int startCol) {
        Square downFaceSquare = downFace.squares[startRow][startCol];

        if (edgeIsColor(downFaceSquare, upColor)) {
            moveUpEdgeFromBottomToMiddleLayer(startRow, startCol);
        }

        else if (squareIsColor(downFaceSquare, upColor)) {
            Color edgeColor = getEdgeColor(downFace.squares[startRow][startCol]);
            int endRow, endCol;

            if (edgeColor.equals(frontColor)) {
                endRow = TOP_ROW;
                endCol = MIDDLE_COLUMN;
            } else if (edgeColor.equals(leftColor)) {
                endRow = MIDDLE_ROW;
                endCol = LEFT_COLUMN;
            } else if (edgeColor.equals(rightColor)) {
                endRow = MIDDLE_ROW;
                endCol = RIGHT_COLUMN;
            } else {
                endRow = BOTTOM_ROW;
                endCol = MIDDLE_COLUMN;
            }

            positionUpEdgeOnDownFace(startRow, startCol, endRow, endCol);
            moveUpEdgeFromDownFace(endRow, endCol);
        }
    }

    private void positionUpEdgeOnDownFace(int startRow, int startCol, int endRow, int endCol) {
        if (startRow != endRow || startCol != endCol) {
            if (Math.abs(startRow - endRow) == 2 || Math.abs(startCol - endCol) == 2) {
                cube.doubleRotateDownFace();
            } else if ((startRow == TOP_ROW && endCol == RIGHT_COLUMN)
                    || (startCol == RIGHT_COLUMN && endRow == BOTTOM_ROW)
                    || (startRow == BOTTOM_ROW && endCol == LEFT_COLUMN)
                    || (startCol == LEFT_COLUMN && endRow == TOP_ROW)) {
                cube.rotateDownFaceClockwise();
            } else {
                cube.rotateDownFaceCounterclockwise();
            }
        }
    }

    private void moveUpEdgeFromBottomToMiddleLayer(int downFaceRow, int downFaceCol) {
        if (downFaceRow == TOP_ROW) {
            cube.rotateFrontFaceCounterclockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(frontFace, RIGHT_COLUMN);
        } else if (downFaceRow == BOTTOM_ROW) {
            cube.rotateBackFaceClockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(backFace, LEFT_COLUMN);
        } else if (downFaceCol == LEFT_COLUMN) {
            cube.rotateLeftFaceCounterclockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(leftFace, RIGHT_COLUMN);
        } else {
            cube.rotateRightFaceClockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(rightFace, LEFT_COLUMN);
        }
    }

    private void moveUpEdgeFromDownFace(int row, int col) {
        if (row == TOP_ROW) {
            cube.doubleRotateFrontFace();
        } else if (row == BOTTOM_ROW) {
            cube.doubleRotateBackFace();
        } else if (col == LEFT_COLUMN) {
            cube.doubleRotateLeftFace();
        } else if (col == RIGHT_COLUMN) {
            cube.doubleRotateRightFace();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Move up face edges from middle layer">
    private void moveUpFaceEdgesFromMiddleLayer() {
        moveUpFaceEdgeFromMiddleLayerToDownFace(frontFace, LEFT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(frontFace, RIGHT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(leftFace, LEFT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(leftFace, RIGHT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(rightFace, LEFT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(rightFace, RIGHT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(backFace, LEFT_COLUMN);
        moveUpFaceEdgeFromMiddleLayerToDownFace(backFace, RIGHT_COLUMN);
    }

    private void moveUpFaceEdgeFromMiddleLayerToDownFace(Face face, int col) {
        if (squareIsColor(face.squares[MIDDLE_ROW][col], upColor)) {
            boolean leftPositioned = edgeIsPositioned(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], upColor, leftColor);
            boolean rightPositioned = edgeIsPositioned(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN], upColor, rightColor);
            boolean topPositioned = edgeIsPositioned(upFace.squares[TOP_ROW][MIDDLE_COLUMN], upColor, backColor);
            boolean bottomPositioned = edgeIsPositioned(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], upColor, frontColor);

            if (face.equals(frontFace)) {
                if (col == LEFT_COLUMN) {
                    cube.rotateLeftFaceClockwise();
                    cube.rotateDownFaceClockwise();
                    if (leftPositioned) {
                        cube.rotateLeftFaceCounterclockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(TOP_ROW, MIDDLE_COLUMN);
                } else {
                    cube.rotateRightFaceCounterclockwise();
                    cube.rotateDownFaceCounterclockwise();
                    if (rightPositioned) {
                        cube.rotateRightFaceClockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(TOP_ROW, MIDDLE_COLUMN);
                }
            } else if (face.equals(leftFace)) {
                if (col == LEFT_COLUMN) {
                    cube.rotateBackFaceClockwise();
                    cube.rotateDownFaceClockwise();
                    if (topPositioned) {
                        cube.rotateBackFaceCounterclockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(MIDDLE_ROW, LEFT_COLUMN);
                } else {
                    cube.rotateFrontFaceCounterclockwise();
                    cube.rotateDownFaceCounterclockwise();
                    if (bottomPositioned) {
                        cube.rotateFrontFaceClockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(MIDDLE_ROW, LEFT_COLUMN);
                }
            } else if (face.equals(rightFace)) {
                if (col == LEFT_COLUMN) {
                    cube.rotateFrontFaceClockwise();
                    cube.rotateDownFaceClockwise();
                    if (bottomPositioned) {
                        cube.rotateFrontFaceCounterclockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(MIDDLE_ROW, RIGHT_COLUMN);
                } else {
                    cube.rotateBackFaceCounterclockwise();
                    cube.rotateDownFaceCounterclockwise();
                    if (topPositioned) {
                        cube.rotateBackFaceClockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(MIDDLE_ROW, RIGHT_COLUMN);
                }
            } else {
                if (col == LEFT_COLUMN) {
                    cube.rotateRightFaceClockwise();
                    cube.rotateDownFaceClockwise();
                    if (rightPositioned) {
                        cube.rotateRightFaceCounterclockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(BOTTOM_ROW, MIDDLE_COLUMN);
                } else {
                    cube.rotateLeftFaceCounterclockwise();
                    cube.rotateDownFaceCounterclockwise();
                    if (leftPositioned) {
                        cube.rotateLeftFaceClockwise();
                    }
                    moveUpFaceEdgeFromBottomLayer(BOTTOM_ROW, MIDDLE_COLUMN);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Move up face edges from top layer">
    private void moveUpFaceEdgesFromTopLayer() {
        if (edgeIsColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], upColor)) {
            cube.rotateLeftFaceCounterclockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(leftFace, RIGHT_COLUMN);
        }
        if (edgeIsColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], upColor)) {
            cube.rotateFrontFaceClockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(frontFace, RIGHT_COLUMN);
        }
        if (edgeIsColor(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN], upColor)) {
            cube.rotateRightFaceCounterclockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(rightFace, LEFT_COLUMN);
        }
        if (edgeIsColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN], upColor)) {
            cube.rotateBackFaceClockwise();
            moveUpFaceEdgeFromMiddleLayerToDownFace(leftFace, RIGHT_COLUMN);
        }
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Position up corners">
    private void positionUpCorners() {
        while (!upCornersAreOriented()) {
            setUpFaceBottomRightCorner();
            if (!upCornersAreOriented()) {
                turnCubeLeft();
            }
        }
    }

    private void setUpFaceBottomRightCorner() {
        Square square = upFace.squares[BOTTOM_ROW][RIGHT_COLUMN];

        if (!upFaceBottomRightCornerInCorrectOrientation()) {
            moveUpFaceBottomRightCornerFromBottomLayer();
            if (cornerHasColor(square, upColor) && !upFaceBottomRightCornerInCorrectOrientation()) {
                orientUpFaceCornerFromRightFace();
            }
        }
    }

    private void moveUpFaceBottomRightCornerFromBottomLayer() {
        Square topRight = downFace.squares[TOP_ROW][RIGHT_COLUMN];
        Square topLeft = downFace.squares[TOP_ROW][LEFT_COLUMN];
        Square bottomRight = downFace.squares[BOTTOM_ROW][RIGHT_COLUMN];
        Square bottomLeft = downFace.squares[BOTTOM_ROW][LEFT_COLUMN];


        if (cornerBelongsInUpFaceBottomRight(topLeft)) {
            cube.rotateDownFaceClockwise();
        } else if (cornerBelongsInUpFaceBottomRight(bottomRight)) {
            cube.rotateDownFaceCounterclockwise();
        } else if (cornerBelongsInUpFaceBottomRight(bottomLeft)) {
            cube.doubleRotateDownFace();
        }
        if (cornerBelongsInUpFaceBottomRight(topRight)) {
            if (squareIsColor(rightFace.squares[BOTTOM_ROW][LEFT_COLUMN], upColor)) {
                orientUpFaceCornerFromRightFace();
            } else if (squareIsColor(frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN], upColor)) {
                orientUpFaceCornerFromFrontFace();
            } else {
                orientUpFaceCornerFromDownFace();
            }
        }
    }

    private void orientUpFaceCornerFromRightFace() {
        cube.rotateRightFaceCounterclockwise();
        cube.rotateDownFaceCounterclockwise();
        cube.rotateRightFaceClockwise();
    }

    private void orientUpFaceCornerFromFrontFace() {
        cube.rotateFrontFaceClockwise();
        cube.rotateDownFaceClockwise();
        cube.rotateFrontFaceCounterclockwise();
    }

    private void orientUpFaceCornerFromDownFace() {
        cube.rotateRightFaceCounterclockwise();
        cube.doubleRotateDownFace();
        cube.rotateRightFaceClockwise();
        cube.rotateDownFaceClockwise();
        orientUpFaceCornerFromRightFace();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Solve middle layer">
    private void solveMiddleLayer() {
        alignMiddleLayerCenterSquares();
    }

    private void alignMiddleLayerCenterSquares() {
        if (!squareIsColor(frontFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], frontColor)) {
            if (squareIsColor(leftFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], frontColor)) {
                cube.sliceEquatorialLayerClockwise();
            } else if (squareIsColor(rightFace.squares[MIDDLE_ROW][MIDDLE_COLUMN], frontColor)) {
                cube.sliceEquatorialLayerCounterclockwise();
            } else {
                cube.doubleSliceEquatorialLayer();
            }
        }
    }

    private void moveMiddleSquareDownRight() {
        cube.rotateUpFaceClockwise();
        cube.rotateRightFaceClockwise();
        cube.rotateUpFaceCounterclockwise();
        cube.rotateRightFaceCounterclockwise();
        cube.rotateUpFaceCounterclockwise();
        cube.rotateFrontFaceCounterclockwise();
        cube.rotateUpFaceClockwise();
        cube.rotateFrontFaceClockwise();
    }

    private void moveMiddleSquareDownLeft() {
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

    //<editor-fold desc="Edge methods">
    private boolean upCrossExists() {
        return upFaceCrossExists() && upEdgesAreOriented();
    }

    private boolean upEdgesAreOriented() {
        Color topEdgeColor = getEdgeColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        Color bottomEdgeColor = getEdgeColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        Color leftEdgeColor = getEdgeColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        Color rightEdgeColor = getEdgeColor(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);

        if (bottomEdgeColor.equals(frontColor)) {
            return upEdgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, rightColor, backColor, leftColor);
        } else if (bottomEdgeColor.equals(leftColor)) {
            return upEdgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, frontColor, rightColor, backColor);
        } else if (bottomEdgeColor.equals(rightColor)) {
            return upEdgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, backColor, leftColor, frontColor);
        } else {
            return upEdgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, leftColor, frontColor, rightColor);
        }
    }

    private boolean upEdgesAreColored(Color rightEdge, Color topEdge, Color leftEdge,
                                      Color rightColor, Color topColor, Color leftColor) {
        return rightEdge.equals(rightColor)
                && topEdge.equals(topColor) && leftEdge.equals(leftColor);
    }

    private boolean edgeIsColor(Square square, Color color) {
        return getEdgeColor(square).equals(color);
    }

    private Color getEdgeColor(Square square) {
        return edgesMap.get(square).getColor();
    }

    private boolean upFaceCrossExists() {
        return squareIsColor(upFace.squares[TOP_ROW][MIDDLE_COLUMN], upColor)
                && squareIsColor(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], upColor)
                && squareIsColor(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN], upColor)
                && squareIsColor(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], upColor);
    }

    private boolean edgeIsPositioned(Square square, Color squareColor, Color edgeColor) {
        return squareIsColor(square, squareColor) && edgeIsColor(square, edgeColor);
    }
    //</editor-fold>

    //<editor-fold desc="Corner methods">
    private boolean upCornersAreOriented() {
        return squareIsColor(upFace.squares[TOP_ROW][LEFT_COLUMN], upColor) && squareIsColor(leftFace.squares[TOP_ROW][LEFT_COLUMN], leftColor)
                && squareIsColor(backFace.squares[TOP_ROW][RIGHT_COLUMN], backColor)

                && squareIsColor(upFace.squares[TOP_ROW][RIGHT_COLUMN], upColor)
                && squareIsColor(rightFace.squares[TOP_ROW][RIGHT_COLUMN], rightColor)
                && squareIsColor(backFace.squares[TOP_ROW][LEFT_COLUMN], backColor)

                && squareIsColor(upFace.squares[BOTTOM_ROW][LEFT_COLUMN], upColor)
                && squareIsColor(frontFace.squares[TOP_ROW][LEFT_COLUMN], frontColor)
                && squareIsColor(leftFace.squares[TOP_ROW][RIGHT_COLUMN], leftColor)

                && upFaceBottomRightCornerInCorrectOrientation();
    }

    private boolean cornerHasColor(Square square, Color color) {
        Square[] corner = cornersMap.get(square);

        return squareIsColor(square, color)
                || squareIsColor(corner[0], color)
                || squareIsColor(corner[1], color);
    }

    private boolean cornerBelongsInUpFaceBottomRight(Square square) {
        return cornerHasColor(square, upColor)
                && cornerHasColor(square, frontColor) && cornerHasColor(square, rightColor);
    }

    private boolean upFaceBottomRightCornerInCorrectOrientation() {
        return squareIsColor(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN], upColor)
                && squareIsColor(frontFace.squares[TOP_ROW][RIGHT_COLUMN], frontColor)
                && squareIsColor(rightFace.squares[TOP_ROW][LEFT_COLUMN], rightColor);
    }
    //</editor-fold>

    private boolean squareIsColor(Square square, Color color) {
        return square.getColor().equals(color);
    }

    private void turnCubeLeft() {
        cube.turnCubeLeft();

        Color temp = leftColor;
        leftColor = frontColor;
        frontColor = rightColor;
        rightColor = backColor;
        backColor = temp;
    }

    private void turnCubeDownwards() {
        cube.turnCubeDown();

        Color temp = upColor;
        upColor = backColor;
        backColor = downColor;
        downColor = frontColor;
        frontColor = temp;
    }
}
