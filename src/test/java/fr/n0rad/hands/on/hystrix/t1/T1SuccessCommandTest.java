package fr.n0rad.hands.on.hystrix.t1;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.Greeting;
import rx.Observable;
import rx.util.functions.Action1;

public class T1SuccessCommandTest {
    @Test
    public void should_get_greeting() {
        Greeting greeting = new T1SuccessCommand().execute();

        Assertions.assertThat(greeting).isNotNull();
        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Bonjour Arnaud, laisse moi un message");
    }

    @Test
    public void should_get_greeting_async() throws Exception {
        Future<Greeting> queue = new T1SuccessCommand().queue();

        Greeting greeting = queue.get(1, TimeUnit.SECONDS);

        Assertions.assertThat(greeting).isNotNull();
        Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Bonjour Arnaud, laisse moi un message");
    }

    @Test
    public void should_observer_greeting() throws Exception {
        Observable<Greeting> observe = new T1SuccessCommand().observe();
        final boolean[] done = {false};

        observe.subscribe(new Action1<Greeting>() {
            @Override
            public void call(Greeting greeting) {
                Assertions.assertThat(greeting.getGreetingText()).isEqualTo("Bonjour Arnaud, laisse moi un message");
                done[0] = true;
            }
        });

        Thread.sleep(1000);
        Assertions.assertThat(done[0]).isTrue();
    }

// just to know :
//
//    new Observer<String>() {
//
//        @Override
//        public void onCompleted() {
//            // nothing needed here
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            e.printStackTrace();
//        }
//
//        @Override
//        public void onNext(String v) {
//            System.out.println("onNext: " + v);
//        }
//
//    }
}
