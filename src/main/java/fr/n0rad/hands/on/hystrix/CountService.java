package fr.n0rad.hands.on.hystrix;


public class CountService {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
