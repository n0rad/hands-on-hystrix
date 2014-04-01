package fr.n0rad.hands.on.hystrix.t99.voicemail.command;


import java.util.ArrayList;
import java.util.List;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.t99.storage.Message;
import fr.n0rad.hands.on.hystrix.t99.storage.Storage;


public class MessagesCommand extends HystrixCommand<List<Message>> {

    private static HystrixCommandGroupKey group = HystrixCommandGroupKey.Factory.asKey("storage");

    private Storage storage;
    private String recipient;

    public MessagesCommand(Storage storage, String recipient) {
        super(group);
        this.storage = storage;
        this.recipient = recipient;
    }

    @Override
    protected List<Message> run() throws Exception {
        return storage.getMessages(recipient);
    }

    @Override
    protected List<Message> getFallback() {
        return new ArrayList<>();
    }
}
