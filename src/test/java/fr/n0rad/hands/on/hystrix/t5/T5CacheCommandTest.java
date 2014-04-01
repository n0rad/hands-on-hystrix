package fr.n0rad.hands.on.hystrix.t5;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import fr.n0rad.hands.on.hystrix.CountService;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T5CacheCommandTest {

    @Test
    public void should_cache_greeting_retreave() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            CountService countService = new CountService();

            Greeting greeting1 = new T5CacheCommand("Arnaud", countService).execute();
            Greeting greeting2 = new T5CacheCommand("Arnaud", countService).execute();

            Assertions.assertThat(greeting1).isSameAs(greeting2);
            Assertions.assertThat(countService.getCount()).isEqualTo(1);
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void should_flush_cache() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            CountService countService = new CountService();

            Greeting greeting1 = new T5CacheCommand("Arnaud", countService).execute();
            T5CacheCommand.flushCache("Arnaud");
            Greeting greeting2 = new T5CacheCommand("Arnaud", countService).execute();

            Assertions.assertThat(greeting1).isEqualTo(greeting2);
            Assertions.assertThat(countService.getCount()).isEqualTo(2);
        } finally {
            context.shutdown();
        }
    }
}
