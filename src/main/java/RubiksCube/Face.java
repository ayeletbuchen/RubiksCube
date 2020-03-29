package RubiksCube;

import javax.swing.*;
import java.awt.*;

public class Face extends JPanel {

    private Square[][] squares;
    final int SIZE = CubeValues.DIMENSION.getValue();
    private final int LEFT_COLUMN = CubeValues.LEFT_COLUMN.getValue();
    private final int MIDDLE_COLUMN = CubeValues.MIDDLE_COLUMN.getValue();
    private final int RIGHT_COLUMN = CubeValues.RIGHT_COLUMN.getValue();
    private final int UP_ROW = CubeValues.UP_ROW.getValue();
    private final int MIDDLE_ROW = CubeValues.MIDDLE_ROW.getValue();
    private final int BOTTOM_ROW = CubeValues.BOTTOM_ROW.getValue();
    private final int MARGIN = FrameValues.FACE_MARGIN;

    public Face(Color color) {
        setLayout(new GridLayout(SIZE, SIZE, MARGIN, MARGIN));
        setSize(FrameValues.FACE_WIDTH, FrameValues.FACE_WIDTH);
        setBackground(Color.BLACK);

        squares = new Square[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new Square(color);
                add(squares[row][col]);
            }
        }
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
        squares[UP_ROW][column].setColor(newColumn[UP_ROW].getColor());
        squares[MIDDLE_ROW][column].setColor(newColumn[MIDDLE_ROW].getColor());
        squares[BOTTOM_ROW][column].setColor(newColumn[BOTTOM_ROW].getColor());
    }

    public void setColumn(int column, Square upSquare, Square middleSquare, Square bottomSquare) {
        squares[UP_ROW][column].setColor(upSquare.getColor());
        squares[MIDDLE_ROW][column].setColor(middleSquare.getColor());
        squares[BOTTOM_ROW][column].setColor(bottomSquare.getColor());
    }

    public void rotateClockwise() {
        Square[][] squaresCopy = deepCopy();

        setRow(UP_ROW,
                squaresCopy[BOTTOM_ROW][LEFT_COLUMN],
                squaresCopy[MIDDLE_ROW][LEFT_COLUMN],
                squaresCopy[UP_ROW][LEFT_COLUMN]);
        squares[MIDDLE_ROW][LEFT_COLUMN].setColor(squaresCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        squares[MIDDLE_ROW][RIGHT_COLUMN].setColor(squaresCopy[UP_ROW][MIDDLE_COLUMN].getColor());
        setRow(BOTTOM_ROW,
                squaresCopy[BOTTOM_ROW][RIGHT_COLUMN],
                squaresCopy[MIDDLE_ROW][RIGHT_COLUMN],
                squaresCopy[UP_ROW][RIGHT_COLUMN]);
    }

    public void rotateCounterclockwise() {
        Square[][] squaresCopy = deepCopy();

        setRow(UP_ROW, squaresCopy[UP_ROW][RIGHT_COLUMN],
                squaresCopy[MIDDLE_ROW][RIGHT_COLUMN],
                squaresCopy[BOTTOM_ROW][RIGHT_COLUMN]);
        squares[MIDDLE_ROW][LEFT_COLUMN].setColor(squaresCopy[UP_ROW][MIDDLE_COLUMN].getColor());
        squares[MIDDLE_ROW][RIGHT_COLUMN].setColor(squaresCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        setRow(BOTTOM_ROW,
                squaresCopy[UP_ROW][LEFT_COLUMN],
                squaresCopy[MIDDLE_ROW][LEFT_COLUMN],
                squaresCopy[BOTTOM_ROW][LEFT_COLUMN]);
    }

    public Square[] getRow(int row) {
        return squares[row];
    }

    public Square[] getColumn(int column) {
        Square[] arrColumn = new Square[SIZE];

        for (int row = UP_ROW; row < SIZE; row++) {
            arrColumn[row] = squares[row][column];
        }
        return arrColumn;
    }

    public Square[] getRowDeepCopy(int row) {
        Square[] copy = new Square[SIZE];

        for (int col = 0; col < SIZE; col++) {
            copy[col] = new Square(squares[row][col].getColor());
        }
        return copy;
    }

    public Square[] getColumnDeepCopy(int col) {
        Square[] copy = new Square[SIZE];

        for (int row = 0; row < SIZE; row++) {
            copy[row] = new Square(squares[row][col].getColor());
        }
        return copy;
    }

    public Square[][] deepCopy() {
        Square[][] squareCopies = new Square[SIZE][SIZE];

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squareCopies[row][col] = new Square(squares[row][col].getColor());
            }
        }
        return squareCopies;
    }
}
