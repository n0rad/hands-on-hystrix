package fr.n0rad.hands.on.hystrix.t99;


import static fr.n0rad.hands.on.hystrix.t99.Utils.rest;
import static fr.n0rad.hands.on.hystrix.t99.storage.UserService.ALICE_PHONE_NUMBER;
import static fr.n0rad.hands.on.hystrix.t99.storage.UserService.ARNAUD_PHONE_NUMBER;
import static fr.n0rad.hands.on.hystrix.t99.storage.UserService.BOB_PHONE_NUMBER;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import fr.n0rad.hands.on.hystrix.t99.storage.Storage;
import fr.n0rad.hands.on.hystrix.t99.voicemail.Voicemail;
import fr.n0rad.hands.on.hystrix.t99.voicemail.Voicemail.CallType;
import fr.norad.jaxrs.client.server.rest.RestSession;

// TODO just naviguate through the tests that simulate a user calling the voicemail server,
// TODO the voicemail server get the info from the storage backend
// TODO the storage backend has 2 bug to demonstrate hystrix :
// TODO - null pointer when reading voicemails on an empty list
// TODO - when interacting with BOB, the backend will lag and timeout
//
// TODO run an hystrix dashboard and run tests to see how it works
public class VoicemailPlatformIT {

    public Storage storage = rest().buildClient(Storage.class, VoicemailPlatform.STORAGE_URL);
    public Voicemail voicemail = rest().buildClient(Voicemail.class, VoicemailPlatform.VOICEMAIL_URL, new RestSession().asJson());

    @Before
    public void before() {
        storage.clear();
    }

    @Test
    public void should_not_fail_if_bug_in_app() throws Exception { // bug is in storageResource.getMessages()
        String greeting = voicemail.getWelcome(ALICE_PHONE_NUMBER, "+333", CallType.CONSULT);
        Assertions.assertThat(greeting).isEqualTo("You have 0 new messages");
    }

    @Test
    public void should_deposit_voicemail_and_read_messages() {
        String success = voicemail.depositVoicemail(ALICE_PHONE_NUMBER, ARNAUD_PHONE_NUMBER, "Salut alice");
        Assertions.assertThat(success).isEqualTo("Thank you for your message");

        String welcome = voicemail.getWelcome(ALICE_PHONE_NUMBER, "+333", CallType.CONSULT);
        Assertions.assertThat(welcome).isEqualTo("You have 1 new messages");

        String messages = voicemail.consultMessages(ALICE_PHONE_NUMBER);
        Assertions.assertThat(messages).isEqualTo("Message from Arnaud: Salut alice. ");
    }

    @Test
    public void should_timeout_db_access_while_deposit_and_read() {
        String success = voicemail.depositVoicemail(ALICE_PHONE_NUMBER, BOB_PHONE_NUMBER, "Salut alice");
        Assertions.assertThat(success).isEqualTo("Sorry we cannot take your message right now");

        String welcome = voicemail.getWelcome(ALICE_PHONE_NUMBER, "+333", CallType.CONSULT);
        Assertions.assertThat(welcome).isEqualTo("You have 0 new messages");
    }

    @Test
    public void should_get_greeting_with_fullname() {
        String welcome = voicemail.getWelcome(ALICE_PHONE_NUMBER, ARNAUD_PHONE_NUMBER, CallType.DEPOSIT);

        Assertions.assertThat(welcome).isEqualTo("Hello Arnaud, Alice is not available. Please leave a message.");
    }

    @Test
    public void should_get_greeting_with_fallback_on_default() {
        String welcome = voicemail.getWelcome(ALICE_PHONE_NUMBER, BOB_PHONE_NUMBER, CallType.DEPOSIT);

        Assertions.assertThat(welcome).isEqualTo("Hello your correspondent is not available. Please leave a message.");
    }

}
