import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
public class CounterUsingMutexTest {
    @Test
    public void blockOnMoreThreads() {
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CounterUsingMutex counterUsingMutex = new CounterUsingMutex();
        IntStream.range(0, count).forEach(user -> executorService.execute(
                () -> {
                    try {
                        counterUsingMutex.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ));
        executorService.shutdown();
        assertTrue(counterUsingMutex.hasQueuedThreads());
    }

    @Test
    public void shouldNotBlockOnThreadsWithDelay() throws InterruptedException {
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CounterUsingMutex counterUsingMutex = new CounterUsingMutex();
        IntStream.range(0, count).forEach(user -> executorService.execute(
                () -> {
                    try {
                        counterUsingMutex.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ));
        executorService.shutdown();


        assertTrue(counterUsingMutex.hasQueuedThreads());
        Thread.sleep(5000);
        assertFalse(counterUsingMutex.hasQueuedThreads());
        assertEquals(5, counterUsingMutex.getCount());
    }
}
