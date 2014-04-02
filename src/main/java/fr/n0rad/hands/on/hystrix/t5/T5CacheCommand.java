package fr.n0rad.hands.on.hystrix.t5;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import fr.n0rad.hands.on.hystrix.CountService;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T5CacheCommand extends HystrixCommand<Greeting> {

    private String name;
    private CountService countService;
    private static HystrixCommandGroupKey group = HystrixCommandGroupKey.Factory.asKey("group");
    private static HystrixCommandKey key = HystrixCommandKey.Factory.asKey("T5Greeting");

    protected T5CacheCommand(String name, CountService countService) {
        super(Setter.withGroupKey(group).andCommandKey(key));
        this.name = name;
        this.countService = countService;
    }

    @Override
    protected Greeting run() throws Exception {
        Greeting greeting = new Greeting("Bonjour " + name + ", laisse moi un message");
        countService.increment();
        return greeting;
    }

    // TODO you need a cache key

    public static void flushCache(String name) {
        // TODO flush cache
    }
}
