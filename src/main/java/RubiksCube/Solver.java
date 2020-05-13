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
        // solveMiddleLayer();
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
        positionWhiteCenterSquare();
        createWhiteCross();
        // putWhiteCornersInPlace();
    }

    //<editor-fold defaultstate="collapsed" desc="Position white center square">
    private void positionWhiteCenterSquare() {
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
        if (!edgesAreOriented(upFace, upColor)) {
            moveEdgeSquaresFromBottomLayerToUpFace();
//            moveWhiteEdgeSquaresFromMiddleLayerToUpFace();
//            moveWhiteEdgeSquaresFromTopLayerToUpFace();
        }
    }

    //<editor-fold desc="Move up face edges from bottom layer">
    private void moveEdgeSquaresFromBottomLayerToUpFace() {
        moveUpFaceSquareFromBottomLayer(TOP_ROW, MIDDLE_COLUMN);
        moveUpFaceSquareFromBottomLayer(MIDDLE_ROW, LEFT_COLUMN);
        moveUpFaceSquareFromBottomLayer(MIDDLE_ROW, RIGHT_COLUMN);
        moveUpFaceSquareFromBottomLayer(BOTTOM_ROW, MIDDLE_COLUMN);
    }

    private void moveUpFaceSquareFromBottomLayer(int startRow, int startCol) {
        boolean upSquareOnBottomLayer = false;
        boolean upSquareOnDownFace = false;
        Square downFaceSquare = downFace.squares[startRow][startCol];
        Color edgeColor = null;
        if (squareIsColor(downFaceSquare, upColor)) {
            edgeColor = getEdgeColor(downFace.squares[startRow][startCol]);
            upSquareOnDownFace = true;
        } else if (edgeIsColor(downFaceSquare, upColor)) {
            edgeColor = downFaceSquare.getColor();
            upSquareOnBottomLayer = true;
        }

        if (upSquareOnDownFace || upSquareOnBottomLayer) {
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

            if (upSquareOnDownFace) {
                if (endRow == TOP_ROW) {
                    cube.doubleRotateFrontFace();
                } else if (endRow == BOTTOM_ROW) {
                    cube.doubleRotateBackFace();
                } else if (endCol == LEFT_COLUMN) {
                    cube.doubleRotateLeftFace();
                } else if (endCol == RIGHT_COLUMN) {
                    cube.doubleRotateRightFace();
                }
            } else if (upSquareOnBottomLayer) {
                cube.rotateDownFaceCounterclockwise();

                if (endCol == LEFT_COLUMN) {
                    cube.rotateBackFaceCounterclockwise();
                    cube.rotateLeftFaceClockwise();
                    cube.rotateBackFaceClockwise();
                } else if (endRow == TOP_ROW) {
                    cube.rotateLeftFaceCounterclockwise();
                    cube.rotateFrontFaceClockwise();
                    cube.rotateLeftFaceClockwise();
                } else if (endCol == RIGHT_COLUMN) {
                    cube.rotateFrontFaceCounterclockwise();
                    cube.rotateRightFaceClockwise();
                    cube.rotateFrontFaceClockwise();
                } else {
                    cube.rotateRightFaceCounterclockwise();
                    cube.rotateBackFaceClockwise();
                    cube.rotateRightFaceClockwise();
                }
            }
        }
    }
    //</editor-fold>

    //</editor-fold>
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Edge methods">
    private boolean edgesAreOriented(Face face, Color color) {
        return crossExists(face, color) && edgesAreOriented(face);
    }

    private boolean edgesAreOriented(Face face) {
        Color topEdgeColor = getEdgeColor(face.squares[TOP_ROW][MIDDLE_COLUMN]);
        Color bottomEdgeColor = getEdgeColor(face.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        Color leftEdgeColor = getEdgeColor(face.squares[MIDDLE_ROW][LEFT_COLUMN]);
        Color rightEdgeColor = getEdgeColor(face.squares[MIDDLE_ROW][RIGHT_COLUMN]);

        if (face.equals(upFace)) {
            if (bottomEdgeColor.equals(frontColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, rightColor, backColor, leftColor);
            } else if (bottomEdgeColor.equals(leftColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, frontColor, rightColor, backColor);
            } else if (bottomEdgeColor.equals(rightColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, backColor, leftColor, frontColor);
            } else {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, leftColor, frontColor, rightColor);
            }
        } else if (face.equals(leftFace)) {
            if (bottomEdgeColor.equals(downColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, frontColor, upColor, backColor);
            } else if (bottomEdgeColor.equals(frontColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, upColor, backColor, downColor);
            } else if (bottomEdgeColor.equals(upColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, backColor, downColor, frontColor);
            } else {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, downColor, frontColor, upColor);
            }
        } else if (face.equals(frontFace)) {
            if (bottomEdgeColor.equals(downColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, rightColor, upColor, leftColor);
            } else if (bottomEdgeColor.equals(rightColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, upColor, leftEdgeColor, downColor);
            } else if (bottomEdgeColor.equals(leftColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, downColor, rightColor, upColor);
            } else {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, leftColor, downColor, rightColor);
            }
        } else if (face.equals(rightFace)) {
            if (bottomEdgeColor.equals(upColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, frontColor, downColor, backColor);
            } else if (bottomEdgeColor.equals(downColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, backColor, upColor, frontColor);
            } else if (bottomEdgeColor.equals(frontColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, downColor, backColor, upColor);
            } else {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, upColor, frontColor, downColor);
            }
        } else if (face.equals(backFace)) {
            if (bottomEdgeColor.equals(downColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, leftColor, upColor, rightColor);
            } else if (bottomEdgeColor.equals(upColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, rightColor, downColor, leftColor);
            } else if (bottomEdgeColor.equals(leftColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, upColor, rightColor, downColor);
            } else {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, downColor, leftColor, upColor);
            }
        } else {
            if (bottomEdgeColor.equals(rightColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, frontColor, leftColor, backColor);
            } else if (bottomEdgeColor.equals(leftColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, backColor, rightColor, frontColor);
            } else if (bottomEdgeColor.equals(frontColor)) {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, leftColor, backColor, rightColor);
            } else {
                return edgesAreColored(rightEdgeColor, topEdgeColor, leftEdgeColor, rightColor, frontColor, leftColor);
            }
        }
    }

    private boolean edgesAreColored(Color rightEdge, Color topEdge, Color leftEdge,
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

    private boolean crossExists(Face face, Color color) {
        return squareIsColor(face.squares[TOP_ROW][MIDDLE_COLUMN], color)
                && squareIsColor(face.squares[MIDDLE_ROW][LEFT_COLUMN], color)
                && squareIsColor(face.squares[MIDDLE_ROW][RIGHT_COLUMN], color)
                && squareIsColor(face.squares[BOTTOM_ROW][MIDDLE_COLUMN], color);
    }
    //</editor-fold>

    private boolean squareIsColor(Square square, Color color) {
        return square.getColor().equals(color);
    }
}
