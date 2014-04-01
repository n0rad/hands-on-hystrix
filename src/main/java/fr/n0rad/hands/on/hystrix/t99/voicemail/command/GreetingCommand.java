package fr.n0rad.hands.on.hystrix.t99.voicemail.command;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.Greeting;
import fr.n0rad.hands.on.hystrix.t99.storage.Storage;

public class GreetingCommand extends HystrixCommand<Greeting> {

    private static HystrixCommandGroupKey group = HystrixCommandGroupKey.Factory.asKey("storage");

    private Storage storage;
    private String caller;
    private String recipient;


    public GreetingCommand(Storage storage, String recipient, String caller) {
        super(group);
        this.storage = storage;
        this.caller = caller;
        this.recipient = recipient;
    }

    @Override
    protected Greeting run() throws Exception {
        return storage.getGreeting(recipient, caller);
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Hello your correspondent is not available. Please leave a message.");
    }
}
