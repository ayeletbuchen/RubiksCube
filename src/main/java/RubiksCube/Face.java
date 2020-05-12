package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class Face extends JPanel implements FrameValues, CubeValues {

    Square[][] squares;
    private final Color ORIGINAL_COLOR;

    public Face(Color color) {
        ORIGINAL_COLOR = color;
        setLayout(new GridLayout(DIMENSION, DIMENSION, SQUARE_MARGIN, SQUARE_MARGIN));
        setSize(FrameValues.FACE_WIDTH, FrameValues.FACE_WIDTH);
        setBackground(Color.BLACK);

        squares = new Square[DIMENSION][DIMENSION];
        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                squares[row][col] = new Square(color);
                add(squares[row][col]);
            }
        }
    }

    public void setFace(Face newFace) {
        setRow(TOP_ROW, newFace.getRow(TOP_ROW));
        setRow(MIDDLE_ROW, newFace.getRow(MIDDLE_ROW));
        setRow(BOTTOM_ROW, newFace.getRow(BOTTOM_ROW));
    }

    public void setFace(Square[][] newSquares) {
        setRow(TOP_ROW, newSquares[TOP_ROW]);
        setRow(MIDDLE_ROW, newSquares[MIDDLE_ROW]);
        setRow(BOTTOM_ROW, newSquares[BOTTOM_ROW]);
    }

    public void setRow(int row, Square[] newRow) {
        squares[row][LEFT_COLUMN].setColor(newRow[LEFT_COLUMN].getColor());
        squares[row][MIDDLE_COLUMN].setColor(newRow[MIDDLE_COLUMN].getColor());
        squares[row][RIGHT_COLUMN].setColor(newRow[RIGHT_COLUMN].getColor());
    }

    public void setRow(int row, Square leftSquare, Square middleSquare, Square rightSquare) {
        squares[row][LEFT_COLUMN].setColor(leftSquare.getColor());
        squares[row][MIDDLE_COLUMN].setColor(middleSquare.getColor());
        squares[row][RIGHT_COLUMN].setColor(rightSquare.getColor());
    }

    public void setColumn(int column, Square[] newColumn) {
        squares[TOP_ROW][column].setColor(newColumn[TOP_ROW].getColor());
        squares[MIDDLE_ROW][column].setColor(newColumn[MIDDLE_ROW].getColor());
        squares[BOTTOM_ROW][column].setColor(newColumn[BOTTOM_ROW].getColor());
    }

    public void setColumn(int column, Square topSquare, Square middleSquare, Square bottomSquare) {
        squares[TOP_ROW][column].setColor(topSquare.getColor());
        squares[MIDDLE_ROW][column].setColor(middleSquare.getColor());
        squares[BOTTOM_ROW][column].setColor(bottomSquare.getColor());
    }

    public void rotateClockwise() {
        Square[][] squaresCopy = deepCopy();

        setRow(TOP_ROW,
                squaresCopy[BOTTOM_ROW][LEFT_COLUMN],
                squaresCopy[MIDDLE_ROW][LEFT_COLUMN],
                squaresCopy[TOP_ROW][LEFT_COLUMN]);
        squares[MIDDLE_ROW][LEFT_COLUMN].setColor(squaresCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        squares[MIDDLE_ROW][RIGHT_COLUMN].setColor(squaresCopy[TOP_ROW][MIDDLE_COLUMN].getColor());
        setRow(BOTTOM_ROW,
                squaresCopy[BOTTOM_ROW][RIGHT_COLUMN],
                squaresCopy[MIDDLE_ROW][RIGHT_COLUMN],
                squaresCopy[TOP_ROW][RIGHT_COLUMN]);
    }

    public void rotateCounterclockwise() {
        Square[][] squaresCopy = deepCopy();

        setRow(TOP_ROW, squaresCopy[TOP_ROW][RIGHT_COLUMN],
                squaresCopy[MIDDLE_ROW][RIGHT_COLUMN],
                squaresCopy[BOTTOM_ROW][RIGHT_COLUMN]);
        squares[MIDDLE_ROW][LEFT_COLUMN].setColor(squaresCopy[TOP_ROW][MIDDLE_COLUMN].getColor());
        squares[MIDDLE_ROW][RIGHT_COLUMN].setColor(squaresCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        setRow(BOTTOM_ROW,
                squaresCopy[TOP_ROW][LEFT_COLUMN],
                squaresCopy[MIDDLE_ROW][LEFT_COLUMN],
                squaresCopy[BOTTOM_ROW][LEFT_COLUMN]);
    }

    public Square[] getRow(int row) {
        return squares[row];
    }

    public Square[] getColumn(int column) {
        Square[] arrColumn = new Square[DIMENSION];

        for (int row = TOP_ROW; row < DIMENSION; row++) {
            arrColumn[row] = squares[row][column];
        }
        return arrColumn;
    }

    public Square[] getRowDeepCopy(int row) {
        Square[] copy = new Square[DIMENSION];

        for (int col = 0; col < DIMENSION; col++) {
            copy[col] = new Square(squares[row][col].getColor());
        }
        return copy;
    }

    public Square[] getColumnDeepCopy(int col) {
        Square[] copy = new Square[DIMENSION];

        for (int row = 0; row < DIMENSION; row++) {
            copy[row] = new Square(squares[row][col].getColor());
        }
        return copy;
    }

    public Square[][] deepCopy() {
        Square[][] squareCopies = new Square[DIMENSION][DIMENSION];

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squareCopies[row][col] = new Square(squares[row][col].getColor());
            }
        }
        return squareCopies;
    }

    public void reset() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squares[row][col].setColor(ORIGINAL_COLOR);
            }
        }
    }
}
