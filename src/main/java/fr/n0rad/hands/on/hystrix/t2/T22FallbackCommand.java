package fr.n0rad.hands.on.hystrix.t2;

import static com.netflix.hystrix.HystrixCommandProperties.Setter;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T22FallbackCommand extends HystrixCommand<Greeting> {
    protected T22FallbackCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("T22Greeting"))
                .andCommandPropertiesDefaults(Setter().withExecutionIsolationThreadTimeoutInMilliseconds(2000)));
    }

    @Override
    protected Greeting run() throws Exception {
        Thread.sleep(1500);
        return new Greeting("Bonjour Arnaud, laisse moi un message");
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Bonjour, laisse moi un message");
    }
}
