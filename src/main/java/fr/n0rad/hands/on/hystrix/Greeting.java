package fr.n0rad.hands.on.hystrix;

public class Greeting {
    private String greetingText;

    public Greeting() {
    }

    public Greeting(String greetingText) {
        this.greetingText = greetingText;
    }

    // lombok...
    public String getGreetingText() {
        return greetingText;
    }

    @Override
    public String toString() {
        return greetingText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Greeting greeting = (Greeting) o;

        if (greetingText != null ? !greetingText.equals(greeting.greetingText) : greeting.greetingText != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return greetingText != null ? greetingText.hashCode() : 0;
    }


}
