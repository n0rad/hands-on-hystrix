package fr.n0rad.hands.on.hystrix.t4;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T4BadRequestCommand extends HystrixCommand<Greeting> {


    private String configurationName = "{bad name";

    protected T4BadRequestCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("T4Greeting"));
    }

    @Override
    protected Greeting run() throws Exception {
        return new Greeting("Bonjour " + configurationName + ", laisse moi un message");
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Bonjour, laisse moi un message");
    }
}
