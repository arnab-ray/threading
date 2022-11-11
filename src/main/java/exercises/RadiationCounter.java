package exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author arnab.ray
 * @created on 09/11/22
 */
class Counter {
    private int count = 0;
    private final Random random = new Random(47);

    public synchronized int get() { return this.count; }

    public synchronized int increment() {
        int temp = this.count;
        if (random.nextBoolean()) {
            Thread.yield();
        }
        return (this.count = ++temp);
    }
}

class Sensor implements Runnable {
    private static final List<Sensor> sensors = new ArrayList<>();
    private static volatile boolean canceled = false;
    private static final Counter counter = new Counter();
    private final int id;
    private int value;

    public static void cancel() { canceled = true; }

    public Sensor(int id) {
        this.id = id;
        sensors.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++value;
            }
            System.out.println(this + " Total: " + counter.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
            }
        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue() { return this.value; }

    public String toSting() { return "Sensor " + id + ": " + getValue(); }

    public static int getTotalCount() { return counter.get(); }

    public static int sumSensors() {
        int sum = 0;
        for (Sensor sensor : sensors)
            sum += sensor.getValue();

        return sum;
    }
}

public class RadiationCounter {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            executorService.execute(new Sensor(i));

        TimeUnit.SECONDS.sleep(1);
        Sensor.cancel();
        executorService.shutdown();
        if (!executorService.awaitTermination(250, TimeUnit.MILLISECONDS))
            System.out.println("Some tasks were not terminated");
        System.out.println("Total: " + Sensor.getTotalCount());
        System.out.println("Sum of Sensors: " + Sensor.sumSensors());
    }
}
