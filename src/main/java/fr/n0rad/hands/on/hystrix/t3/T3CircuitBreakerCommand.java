package fr.n0rad.hands.on.hystrix.t3;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.CountService;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T3CircuitBreakerCommand extends HystrixCommand<Greeting> {

    private CountService countService;

    protected T3CircuitBreakerCommand(CountService countService) {
        super(HystrixCommandGroupKey.Factory.asKey("T3Greeting"));
        this.countService = countService;
    }

    @Override
    protected Greeting run() throws Exception {
        Thread.sleep(2000);
        Greeting greeting = new Greeting("Bonjour Arnaud, laisse moi un message");
        countService.increment();
        return greeting;
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Bonjour, laisse moi un message");
    }
}
