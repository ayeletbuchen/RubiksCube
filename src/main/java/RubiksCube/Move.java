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
    Y_PRIME("Turn cube to the right", "Turn cube right"),
    Y("Turn cube to the left", "Turn cube left"),
    X("Turn cube upwards", "Turn cube upwards"),
    X_PRIME("Turn cube downwards", "Turn cube downwards"),
    Z("Turn cube clockwise along Z axis", "Z axis clockwise turn"),
    Z_PRIME("Turn cube counterclockwise along Z axis", "Z axis counterclockwise turn"),
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

    private void setCounterMove(Move counterMove) {
        this.counterMove = counterMove;
    }

    public static void setCounterMoves() {
        Move.U.setCounterMove(Move.U_PRIME);
        Move.L.setCounterMove(Move.L_PRIME);
        Move.F.setCounterMove(Move.F_PRIME);
        Move.R.setCounterMove(Move.R_PRIME);
        Move.B.setCounterMove(Move.B_PRIME);
        Move.D.setCounterMove(Move.D_PRIME);
        Move.U_PRIME.setCounterMove(Move.U);
        Move.L_PRIME.setCounterMove(Move.L);
        Move.F_PRIME.setCounterMove(Move.F);
        Move.R_PRIME.setCounterMove(Move.R);
        Move.B_PRIME.setCounterMove(Move.B);
        Move.D_PRIME.setCounterMove(Move.D);
        Move.M.setCounterMove(Move.M_PRIME);
        Move.E.setCounterMove(Move.E_PRIME);
        Move.S.setCounterMove(Move.S_PRIME);
        Move.M_PRIME.setCounterMove(Move.M);
        Move.E_PRIME.setCounterMove(Move.E);
        Move.S_PRIME.setCounterMove(Move.S);
        Move.Y_PRIME.setCounterMove(Move.Y);
        Move.Y.setCounterMove(Move.Y_PRIME);
        Move.X.setCounterMove(Move.X_PRIME);
        Move.X_PRIME.setCounterMove(Move.X);
        Move.Z.setCounterMove(Move.Z_PRIME);
        Move.Z_PRIME.setCounterMove(Move.Z);
    }
}
