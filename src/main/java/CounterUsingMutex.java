import java.util.concurrent.Semaphore;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
public class CounterUsingMutex {
    private final Semaphore semaphore;
    private int count;

    public CounterUsingMutex() {
        semaphore = new Semaphore(1);
        count = 0;
    }

    void increase() throws InterruptedException {
        semaphore.acquire();
        count++;
        Thread.sleep(1000);
        semaphore.release();
    }

    int getCount() {
        return this.count;
    }

    boolean hasQueuedThreads() {
        return semaphore.hasQueuedThreads();
    }
}
