package fr.n0rad.hands.on.hystrix.t9;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.CountService;
import fr.n0rad.hands.on.hystrix.Greeting;

public class T99OpenCircuitCommandTest {

    @Test // prevent overloading of the service if the number of fail is already too high
    public void should_open_circuit_on_10_request_volume_threshold() throws Exception {
        CountService countService = new CountService();

        new T99OpenCircuitCommand(countService, false).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, false).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, false).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        new T99OpenCircuitCommand(countService, true).execute();
        Thread.sleep(100);
        Greeting greeting = new T99OpenCircuitCommand(countService, true).execute();

        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Salut, laisse moi un message");
        Assertions.assertThat(countService.getCount()).isEqualTo(10);
    }
}
