package fr.n0rad.hands.on.hystrix.t4;

import org.junit.Test;
import com.netflix.hystrix.exception.HystrixBadRequestException;

public class T4BadRequestCommandTest {

    @Test(expected = HystrixBadRequestException.class)
    public void should_break_circuit() {
        new T4BadRequestCommand().execute();
    }
}
