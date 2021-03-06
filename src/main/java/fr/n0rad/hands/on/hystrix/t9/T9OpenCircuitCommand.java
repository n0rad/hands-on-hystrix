package fr.n0rad.hands.on.hystrix.t9;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T9OpenCircuitCommand extends HystrixCommand<Greeting> {
    public T9OpenCircuitCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("T9Greeting"));
        // TODO configure the command as an open circuit
    }

    @Override
    protected Greeting run() throws Exception {
        return new Greeting("Bonjour Arnaud, laisse moi un message");
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Salut, laisse moi un message");
    }
}
