package fr.n0rad.hands.on.hystrix.t99.storage;

public class Message {
    public String caller;
    public String text;

    public Message() {
    }

    public Message(String caller, String text) {
        this.caller = caller;
        this.text = text;
    }
}
