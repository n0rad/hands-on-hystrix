package fr.n0rad.hands.on.hystrix.t2;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T2FallbackCommandTest {

    @Test
    public void should_fallback_on_get_greeting() {
        Greeting greeting = new T2FallbackCommand().execute();

        Assertions.assertThat(greeting).isNotNull();
        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Bonjour, laisse moi un message");

    }
}
