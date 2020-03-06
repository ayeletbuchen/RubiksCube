public enum CubeValues {
    TOP_ROW(0),
    MIDDLE_ROW(1),
    BOTTOM_ROW(2),
    LEFT_COLUMN(0),
    MIDDLE_COLUMN(1),
    RIGHT_COLUMN(2),
    DIMENSION(3);

    private int value;

    CubeValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
