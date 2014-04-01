package fr.n0rad.hands.on.hystrix.t6;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T6JmxStatCommand extends HystrixCommand<Greeting> {
    public T6JmxStatCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("T1Greeting"));
    }

    @Override
    protected Greeting run() throws Exception {
        return new Greeting("Bonjour Arnaud, laisse moi un message");
    }
}
