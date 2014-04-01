package fr.n0rad.hands.on.hystrix.t99.voicemail;

import java.util.List;
import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import fr.n0rad.hands.on.hystrix.t99.storage.Message;
import fr.n0rad.hands.on.hystrix.t99.voicemail.command.StorageFactory;

public class VoicemailResource implements Voicemail {

    private StorageFactory storageFactory;

    public VoicemailResource(StorageFactory storageFactory) {
        this.storageFactory = storageFactory;
        HystrixPlugins.getInstance().registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
    }

    @Override
    public String getWelcome(String recipient, String guest, CallType type) {
        if (type == CallType.CONSULT) {
            List<Message> messages = storageFactory.messagesCommand(recipient).execute();
            return "You have " + messages.size() + " new messages";
        } else if (type == CallType.DEPOSIT) {
            return storageFactory.GreetingCommand(recipient, guest).execute().getGreetingText();
        } else {
            return "Sorry unknown call type received";
        }
    }

    @Override
    public String depositVoicemail(String recipient, String guest, String message) {
        return storageFactory.newVoicemailCommand(recipient, guest, message).execute();
    }

    @Override
    public String consultMessages(String recipient) {
        StringBuilder res = new StringBuilder();
        List<Message> messages = storageFactory.messagesCommand(recipient).execute();
        for (Message message : messages) {
            res.append("Message from ");
            res.append(message.caller);
            res.append(": ");
            res.append(message.text);
            res.append(". ");
        }
        return res.toString();
    }
}
