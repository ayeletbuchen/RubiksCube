import java.awt.*;

public enum CubeInfo {
    TOP_ROW(0),
    MIDDLE_ROW(1),
    BOTTOM_ROW(2),
    LEFT_COLUMN(0),
    MIDDLE_COLUMN(1),
    RIGHT_COLUMN(2),
    DIMENSION(3),
    BLUE(Color.BLUE),
    GREEN(Color.GREEN),
    RED(Color.RED),
    WHITE(Color.WHITE),
    YELLOW(Color.YELLOW),
    ORANGE(Color.ORANGE);

    int value;
    Color color;

    CubeInfo(int value) {
        this.value = value;
    }
    CubeInfo(Color color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }
    public Color getColor() {
        return color;
    }
}
