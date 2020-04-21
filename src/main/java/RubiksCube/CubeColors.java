package RubiksCube;

import java.awt.*;

public enum CubeColors {
    BLUE(Color.BLUE),
    GREEN(Color.GREEN),
    RED(Color.RED),
    WHITE(Color.WHITE),
    YELLOW(Color.YELLOW),
    ORANGE(Color.ORANGE);

    private Color color;

    CubeColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
