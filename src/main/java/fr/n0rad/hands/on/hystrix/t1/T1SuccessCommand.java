package fr.n0rad.hands.on.hystrix.t1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T1SuccessCommand extends HystrixCommand<Greeting> {
    public T1SuccessCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("T1Greeting"));
    }

    @Override
    protected Greeting run() throws Exception {
        return new Greeting("Bonjour Arnaud, laisse moi un message");
    }
}
