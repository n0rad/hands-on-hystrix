package fr.n0rad.hands.on.hystrix.t2;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T2FallbackCommand extends HystrixCommand<Greeting> {
    protected T2FallbackCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("T2Greeting"));
    }

    @Override
    protected Greeting run() throws Exception {
        Thread.sleep(1000);
        return new Greeting("Bonjour Arnaud, laisse moi un message");
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Bonjour, laisse moi un message");
    }
}
