package fr.n0rad.hands.on.hystrix.t8;

import java.util.concurrent.Future;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.junit.Test;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import fr.n0rad.hands.on.hystrix.Greeting;

public class t8CommandCollapserTest {

    @Test
    public void should_callapse_some_call() throws Exception {
        HystrixRequestContext context = null;
        try {
            context = HystrixRequestContext.initializeContext();
            Future<Greeting> f1 = new t8CommandCollapser("Arnaud").queue();
            Future<Greeting> f2 = new t8CommandCollapser("Isabelle").queue();
            Future<Greeting> f3 = new t8CommandCollapser("Bob").queue();
            Future<Greeting> f4 = new t8CommandCollapser("Alice").queue();

            int numExecuted = HystrixRequestLog.getCurrentRequest().getExecutedCommands().size();

            if (numExecuted > 2) {
                Fail.fail("some of the commands should have been collapsed");
            }

            Assertions.assertThat(f1.get().getGreetingText()).isEqualTo("hello, Arnaud");
            Assertions.assertThat(f2.get().getGreetingText()).isEqualTo("hello, Isabelle");
            Assertions.assertThat(f3.get().getGreetingText()).isEqualTo("hello, Bob");
            Assertions.assertThat(f4.get().getGreetingText()).isEqualTo("hello, Alice");
        } finally {
            context.shutdown();
        }
    }
}
