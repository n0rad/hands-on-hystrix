package fr.n0rad.hands.on.hystrix.t3;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.CountService;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T3CircuitBreakerCommandTest {

    @Test
    public void should_break_circuit() {
        CountService countService = new CountService();
        Greeting greeting = new T3CircuitBreakerCommand(countService).execute();

        Assertions.assertThat(greeting).isNotNull();
        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Bonjour, laisse moi un message");
        Assertions.assertThat(countService.getCount()).isEqualTo(0);
    }
}
