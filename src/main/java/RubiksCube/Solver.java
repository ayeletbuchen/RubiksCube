package RubiksCube;

import java.util.Stack;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Solver extends Stack<Move> implements Observer<Move> {

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

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(Move move) {
        System.out.println(move.getSymbol());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public void solve() {

    }
}
