package fr.n0rad.hands.on.hystrix.t99.voicemail.command;


import fr.n0rad.hands.on.hystrix.t99.Utils;
import fr.n0rad.hands.on.hystrix.t99.storage.Storage;
import fr.norad.jaxrs.client.server.rest.RestSession;

public class StorageFactory {

    private Storage storage;

    public StorageFactory(String storageUrl) {
        this.storage = Utils.rest().buildClient(Storage.class, storageUrl, new RestSession().asJson());
    }

    public GreetingCommand GreetingCommand(String recipient, String caller) {
        return new GreetingCommand(storage, recipient, caller);
    }

    public newVoicemailCommand newVoicemailCommand(String recipient, String caller, String message) {
        return new newVoicemailCommand(storage, recipient, caller, message);
    }

    public MessagesCommand messagesCommand(String recipient) {
        return new MessagesCommand(storage, recipient);
    }
}
