package fr.n0rad.hands.on.hystrix.t9;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.CountService;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T99OpenCircuitCommand extends HystrixCommand<Greeting> {
    private CountService countService;
    private boolean fail;

    public T99OpenCircuitCommand(CountService countService, boolean fail) {
        super(HystrixCommandGroupKey.Factory.asKey("T99Greeting"));
        // TODO configure command to open circuit when more than 10 req (in 1 sec ?)
        this.countService = countService;
        this.fail = fail;
    }

    @Override
    protected Greeting run() throws Exception {
        countService.increment();
        if (fail) {
            throw new RuntimeException("problem");
        }
        return new Greeting("Bonjour Arnaud, laisse moi un message");
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Salut, laisse moi un message");
    }
}
