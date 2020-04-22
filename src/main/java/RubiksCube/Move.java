package RubiksCube;

public enum Move {

    U("Rotate up face clockwise", "U"),
    L("Rotate left face clockwise", "L"),
    F("Rotate front face clockwise", "F"),
    R("Rotate right face clockwise", "R"),
    B("Rotate back face clockwise", "B"),
    D("Rotate down face clockwise", "D"),
    U_PRIME("Rotate up face counterclockwise", "U'"),
    L_PRIME("Rotate left face counterclockwise", "L'"),
    F_PRIME("Rotate front face counterclockwise", "F'"),
    R_PRIME("Rotate right face counterclockwise", "R'"),
    B_PRIME("Rotate back face counterclockwise", "B'"),
    D_PRIME("Rotate down face counterclockwise", "D'"),
    M("Slice middle layer clockwise", "M"),
    E("Slice equatorial layer clockwise", "E"),
    S("Slice standing layer clockwise", "S"),
    M_PRIME("Slice middle layer counterclockwise", "M'"),
    E_PRIME("Slice equatorial layer counterclockwise", "E'"),
    S_PRIME("Slice standing layer counterclockwise", "S'"),
    CUBE_LEFT_TURN("Turn cube to the left", "Turn cube left"),
    CUBE_RIGHT_TURN("Turn cube to the right", "Turn cube right"),
    CUBE_UP_TURN("Turn cube upwards", "Turn cube upwards"),
    CUBE_DOWN_TURN("Turn cube downwards", "Turn cube downwards"),
    SHUFFLE("Shuffle cube", "Shuffle"),
    RESET("Reset cube", "Reset");

    private String prompt;
    private String symbol;
    private Move counterMove;

    Move(String prompt, String symbol) {
        this.prompt = prompt;
        this.symbol = symbol;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getSymbol() {
        return symbol;
    }

    public Move getCounterMove() {
        return counterMove;
    }

    public void setCounterMove(Move counterMove) {
        this.counterMove = counterMove;
    }
}
