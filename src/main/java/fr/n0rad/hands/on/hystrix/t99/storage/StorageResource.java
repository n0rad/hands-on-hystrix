package fr.n0rad.hands.on.hystrix.t99.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.n0rad.hands.on.hystrix.Greeting;

public class StorageResource implements Storage {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String, List<Message>> usersMessages = new HashMap<>();

    private UserService userService = new UserService();

    @Override
    public Greeting getGreeting(String recipient, String caller) throws UserNotFoundException {
        log.info("get greeting for recipient : " + recipient + " caller : " + caller);
        String callerName;
        try {
            callerName = userService.usernameFromPhoneNumber(caller);
        } catch (UserNotFoundException e) {
            callerName = "";
        }
        return new Greeting("Hello " + callerName + ", " +
                userService.usernameFromPhoneNumber(recipient) + " is not available. Please leave a message.");
    }

    @Override
    public void postVoicemail(String recipient, String caller, String text) throws UserNotFoundException {
        log.info("post voicemail for recipient : " + recipient + " caller : " + caller + " text : " + text);
        String username = userService.usernameFromPhoneNumber(recipient);
        if (usersMessages.get(username) == null) {
            usersMessages.put(username, new ArrayList<Message>());
        }
        usersMessages.get(username).add(new Message(userService.usernameFromPhoneNumber(caller), text));
    }

    @Override
    public List<Message> getMessages(String recipient) throws UserNotFoundException {
        log.info("get messages for recipient : " + recipient);
        List<Message> messages = usersMessages.get(userService.usernameFromPhoneNumber(recipient));
        System.out.println("" + messages.get(0).caller); // bug in the app
        return messages;
    }

    @Override
    public void clear() {
        usersMessages.clear();
    }
}
