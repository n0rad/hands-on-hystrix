package fr.n0rad.hands.on.hystrix.t4;

import org.junit.Test;

public class T4BadRequestCommandTest {

    @Test(expected = Exception.class)
    public void should_break_circuit() {
        new T4BadRequestCommand().execute();
    }
}
