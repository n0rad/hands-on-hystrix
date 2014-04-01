package fr.n0rad.hands.on.hystrix.t99.storage;


import java.util.Map;
import com.google.common.collect.ImmutableMap;

public class UserService {

    public static final String ARNAUD_PHONE_NUMBER = "+33654321";
    public static final String ALICE_PHONE_NUMBER = "+33242424";
    public static final String BOB_PHONE_NUMBER = "+334242";
    public static final String UNKOWN_PHONE_NUMBER = "+3300000";

    private Map<String, String> usernameFromPhoneNumbers = ImmutableMap.of(BOB_PHONE_NUMBER, "Bob",
            ALICE_PHONE_NUMBER, "Alice", ARNAUD_PHONE_NUMBER, "Arnaud");

    public String usernameFromPhoneNumber(String phoneNumber) throws UserNotFoundException {
        String username = usernameFromPhoneNumbers.get(phoneNumber);
        if (username == null) {
            throw new UserNotFoundException("user not found for phoneNumber : " + phoneNumber);
        }
        if ("Bob".equals(username)) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new IllegalStateException();
            }
        }
        return username;
    }

}
