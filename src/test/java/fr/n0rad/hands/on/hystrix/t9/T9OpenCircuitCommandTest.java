package fr.n0rad.hands.on.hystrix.t9;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T9OpenCircuitCommandTest {

    @Test
    public void should_open_circuit() throws Exception {
        Greeting greeting = new T9OpenCircuitCommand().execute();

        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Salut, laisse moi un message");
    }
}
