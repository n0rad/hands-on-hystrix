package fr.n0rad.hands.on.hystrix.t9;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T9OpenCircuitCommand extends HystrixCommand<Greeting> {
    public T9OpenCircuitCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("T9Greeting")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withCircuitBreakerForceOpen(true)));
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
