package fr.n0rad.hands.on.hystrix.t99.voicemail.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import fr.n0rad.hands.on.hystrix.t99.storage.Storage;

public class newVoicemailCommand extends HystrixCommand<String> {

    private static HystrixCommandGroupKey group = HystrixCommandGroupKey.Factory.asKey("storage");

    private Storage storage;
    private String recipient;
    private String caller;
    private String message;

    public newVoicemailCommand(Storage storage, String recipient, String caller, String message) {
        super(group);
        this.storage = storage;
        this.recipient = recipient;
        this.message = message;
        this.caller = caller;
    }

    @Override
    protected String run() throws Exception {
        storage.postVoicemail(recipient, caller, message);
        return "Thank you for your message";
    }

    @Override
    protected String getFallback() {
        return "Sorry we cannot take your message right now";
    }
}