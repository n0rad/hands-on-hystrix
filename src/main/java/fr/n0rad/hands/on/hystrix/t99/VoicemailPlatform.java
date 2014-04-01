package fr.n0rad.hands.on.hystrix.t99;


import static fr.n0rad.hands.on.hystrix.t99.Utils.rest;
import static java.util.Arrays.asList;
import fr.n0rad.hands.on.hystrix.t99.storage.StorageResource;
import fr.n0rad.hands.on.hystrix.t99.voicemail.Metrics;
import fr.n0rad.hands.on.hystrix.t99.voicemail.VoicemailResource;
import fr.n0rad.hands.on.hystrix.t99.voicemail.command.StorageFactory;

public class VoicemailPlatform {

    public static final String STORAGE_URL = "http://localhost:4242/storage";
    public static final String VOICEMAIL_URL = "http://localhost:4243/voicemail";

    public static void main(String[] args) throws Exception {
        StorageFactory storageFactory = new StorageFactory(STORAGE_URL);
        rest().buildServer(STORAGE_URL, new StorageResource());
        rest().buildServer(VOICEMAIL_URL, asList(new VoicemailResource(storageFactory), new Metrics()));
        while (true) {
            Thread.sleep(1000);
        }
    }
}
