public class Face {

    private Square[][] squares;
    final int SIZE = CubeValues.DIMENSION.getValue();
    private final int LEFT_COLUMN = CubeValues.LEFT_COLUMN.getValue();
    private final int MIDDLE_COLUMN = CubeValues.MIDDLE_COLUMN.getValue();
    private final int RIGHT_COLUMN = CubeValues.RIGHT_COLUMN.getValue();
    private final int TOP_ROW = CubeValues.TOP_ROW.getValue();
    private final int MIDDLE_ROW = CubeValues.MIDDLE_ROW.getValue();
    private final int BOTTOM_ROW = CubeValues.BOTTOM_ROW.getValue();

    public Face(Square[][] squares/*Row topRow, Row middleRow, Row bottomRow*/) {
        this.squares = new Square[SIZE][SIZE];
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                this.squares[row][col] = squares[row][col];
            }
        }
    }

    public void setRow(int row, Square[] newRow) {
        squares[row][LEFT_COLUMN] = newRow[LEFT_COLUMN];
        squares[row][MIDDLE_COLUMN] = newRow[MIDDLE_ROW];
        squares[row][RIGHT_COLUMN] = newRow[RIGHT_COLUMN];
    }

    public void setRow(int row, Square leftSquare, Square middleSquare, Square rightSquare) {
        squares[row][LEFT_COLUMN] = leftSquare;
        squares[row][MIDDLE_COLUMN] = middleSquare;
        squares[row][RIGHT_COLUMN] = rightSquare;
    }

    public void setLeftColumn(Square topSquare, Square middleSquare, Square bottomSquare) {
        setColumn(LEFT_COLUMN, topSquare, middleSquare, bottomSquare);
    }

    public void setMiddleColumn(Square topSquare, Square middleSquare, Square bottomSquare) {
        setColumn(MIDDLE_COLUMN, topSquare, middleSquare, bottomSquare);
    }

    public void setRightColumn(Square topSquare, Square middleSquare, Square bottomSquare) {
        setColumn(RIGHT_COLUMN, topSquare, middleSquare, bottomSquare);
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

    public Square[] getTopRow() {
        return squares[TOP_ROW];
    }

    public Square[] getMiddleRow() {
        return squares[MIDDLE_ROW];
    }

    public Square[] getBottomRow() {
        return squares[BOTTOM_ROW];
    }

    public Square[] getRow(int row) {
        return squares[row];
    }

    public Square[] getLeftColumn() {
        Square[] leftColumn = new Square[SIZE];

        for (int row = 0; row < squares.length; row++) {
            leftColumn[row] = squares[row][LEFT_COLUMN];
        }
        return leftColumn;
    }

    public Square[] getMiddleColumn() {
        Square[] middleColumn = new Square[SIZE];

        for (int row = 0; row < squares.length; row++) {
            middleColumn[row] = squares[row][MIDDLE_COLUMN];
        }
        return middleColumn;
    }

    public Square[] getRightColumn() {
        Square[] rightColumn = new Square[SIZE];

        for (int row = 0; row < squares.length; row++) {
            rightColumn[row] = squares[row][RIGHT_COLUMN];
        }
        return rightColumn;
    }

    private Square[][] deepCopy() {
        Square[][] squareCopies = new Square[SIZE][SIZE];

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squareCopies[row][col] = squares[row][col];
            }
        }
        return squareCopies;
    }
}
