package basics;

import java.util.concurrent.TimeUnit;

/**
 * @author arnab.ray
 * @created on 06/11/22
 */
class Sleeper extends Thread {
    private final int duration;

    Sleeper(String name, int sleepTime) {
        super(name);
        this.duration = sleepTime;
        start();
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted. isInterrupted(): " + isInterrupted());
        }
        System.out.println(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private final Sleeper sleeper;

    Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}

public class Joining {
    public static void main(String[] args) {
        Sleeper sleepy = new Sleeper("Sleepy", 1500);
        Sleeper grumpy = new Sleeper("Grumpy", 1000);

        Joiner dopey = new Joiner("Dopey", sleepy);
        Joiner doc = new Joiner("Doc", grumpy);

        grumpy.interrupt();
    }
}
