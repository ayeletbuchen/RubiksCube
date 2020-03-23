public class Face {

    private Square[][] squares;
    final int SIZE = CubeValues.DIMENSION.getValue();
    private final int LEFT_COLUMN = CubeValues.LEFT_COLUMN.getValue();
    private final int MIDDLE_COLUMN = CubeValues.MIDDLE_COLUMN.getValue();
    private final int RIGHT_COLUMN = CubeValues.RIGHT_COLUMN.getValue();
    private final int TOP_ROW = CubeValues.TOP_ROW.getValue();
    private final int MIDDLE_ROW = CubeValues.MIDDLE_ROW.getValue();
    private final int BOTTOM_ROW = CubeValues.BOTTOM_ROW.getValue();

    public Face(Square[][] squares) {
        this.squares = new Square[SIZE][SIZE];
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                this.squares[row][col] = squares[row][col];
            }
        }
    }

    public void setRow(int row, Square[] newRow) {
        squares[row][LEFT_COLUMN] = newRow[LEFT_COLUMN];
        squares[row][MIDDLE_COLUMN] = newRow[MIDDLE_COLUMN];
        squares[row][RIGHT_COLUMN] = newRow[RIGHT_COLUMN];
    }

    public void setRow(int row, Square leftSquare, Square middleSquare, Square rightSquare) {
        squares[row][LEFT_COLUMN] = leftSquare;
        squares[row][MIDDLE_COLUMN] = middleSquare;
        squares[row][RIGHT_COLUMN] = rightSquare;
    }

    public void setColumn(int column, Square[] newColumn) {
        squares[TOP_ROW][column] = newColumn[TOP_ROW];
        squares[MIDDLE_ROW][column] = newColumn[MIDDLE_ROW];
        squares[BOTTOM_ROW][column] = newColumn[BOTTOM_ROW];
    }

    private void setColumn(int column, Square topSquare, Square middleSquare, Square bottomSquare) {
        squares[TOP_ROW][column] = topSquare;
        squares[MIDDLE_ROW][column] = middleSquare;
        squares[BOTTOM_ROW][column] = bottomSquare;
    }

    public void rotateClockwise() {
        Square[][] squaresCopy = deepCopy();

        setRow(TOP_ROW,
                squaresCopy[BOTTOM_ROW][LEFT_COLUMN],
                squaresCopy[MIDDLE_ROW][LEFT_COLUMN],
                squaresCopy[TOP_ROW][LEFT_COLUMN]);
        squares[MIDDLE_ROW][LEFT_COLUMN] = squaresCopy[BOTTOM_ROW][MIDDLE_COLUMN];
        squares[MIDDLE_ROW][RIGHT_COLUMN] = squaresCopy[TOP_ROW][MIDDLE_COLUMN];
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
        squares[MIDDLE_ROW][LEFT_COLUMN] = squaresCopy[TOP_ROW][MIDDLE_COLUMN];
        squares[MIDDLE_ROW][RIGHT_COLUMN] = squaresCopy[BOTTOM_ROW][MIDDLE_COLUMN];
        setRow(BOTTOM_ROW,
                squaresCopy[TOP_ROW][LEFT_COLUMN],
                squaresCopy[MIDDLE_ROW][LEFT_COLUMN],
                squaresCopy[BOTTOM_ROW][LEFT_COLUMN]);
    }

    public Square[] getRow(int row) {
        return squares[row];
    }

    public Square[] getColumn(int column) {
        Square[] arrColumn = new Square[SIZE];

        for (int row = TOP_ROW; row < SIZE; row++) {
            arrColumn[row] = squares[row][column];
        }
        return arrColumn;
    }

    public Square[][] deepCopy() {
        Square[][] squareCopies = new Square[SIZE][SIZE];

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squareCopies[row][col] = squares[row][col];
            }
        }
        return squareCopies;
    }
}
