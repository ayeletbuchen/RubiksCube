package RubiksCube;

import java.util.Stack;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class Solver extends Stack<Move> implements Observer<Move>
        //implements Disposable
        {

    private Face upFace;
    private Face leftFace;
    private Face frontFace;
    private Face rightFace;
    private Face backFace;
    private Face downFace;
    private Disposable disposable = new Disposable() {
        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    };

    // private Observable observable;

    public Solver(Cube cube) {
        // observable = Observable.just(cube);
        // observable.subscribe(System.out::println);
        upFace = cube.getUpFace();
        leftFace = cube.getLeftFace();
        frontFace = cube.getFrontFace();
        rightFace = cube.getRightFace();
        backFace = cube.getBackFace();
        downFace = cube.getDownFace();
        cube.subject.subscribe(this);
        // Action action = () -> cube.sliceMiddleLayerCounterclockwise();
        // Completable completable = Completable.fromAction(action);
        // completable.subscribe(System.out::println);
        // Disposable disposable = cube.turnCubeRight().subscribe(System.out::println);
        // solve();
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
        // solveTopLayer();
    }

    private void solveTopLayer() {

        // while (upFace.squares[CubeValues.MIDDLE_ROW.getValue()][CubeValues.MIDDLE_COLUMN.getValue()].getColor() != CubeColors.WHITE.getColor()) {
        //    System.out.println("turn cube until center of up face is white");
        // }
        // Disposable disposable = cube.subscribe(System.out::println);
        // Disposable disposable = cube.turnCubeRight().subscribeOn(Schedulers.io()).observeOn(Schedulers.trampoline()).subscribe(System.out::println);
    }

//    @Override
//    public void dispose() {
//
//    }
//
//    @Override
//    public boolean isDisposed() {
//        return false;
//    }
}
