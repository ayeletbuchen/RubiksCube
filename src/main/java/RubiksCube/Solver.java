package RubiksCube;

public class Solver {

    private Face upFace;
    private Face leftFace;
    private Face frontFace;
    private Face rightFace;
    private Face backFace;
    private Face downFace;

    public Solver(Cube cube) {
        upFace = cube.getUpFace();
        leftFace = cube.getLeftFace();
        frontFace = cube.getFrontFace();
        rightFace = cube.getRightFace();
        backFace = cube.getBackFace();
        downFace = cube.getDownFace();
    }

    public void solve() {
        solveTopLayer();
    }

    private void solveTopLayer() {
        if (upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor() != CubeColors.WHITE.getColor()) {
            System.out.println("turn cube until white center of up face is white");
        }
    }
}
