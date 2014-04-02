package fr.n0rad.hands.on.hystrix.t2;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T22FallbackCommandTest {

    @Test
    public void should_not_fallback_with_1500ms_isolation_timeout() {
        Greeting greeting = new T22FallbackCommand().execute();

        Assertions.assertThat(greeting).isNotNull();
        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Bonjour Arnaud, laisse moi un message");
    }
}
