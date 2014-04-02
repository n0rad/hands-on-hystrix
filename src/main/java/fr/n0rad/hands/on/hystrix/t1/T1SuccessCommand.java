package fr.n0rad.hands.on.hystrix.t1;

import java.util.concurrent.Future;
import fr.n0rad.hands.on.hystrix.Greeting;
import rx.Observable;

// TODO almost everything in hystrix is around HystrixCommand
public class T1SuccessCommand {

    // TODO just allow compilation, should be removed
    public Greeting execute() {
        return null;
    }

    public Future<Greeting> queue() {
        return null;
    }

    public Observable<Greeting> observe() {
        return null;
    }
}
