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
public class LoginQueueUsingSemaphoreTest {
    @Test
    public void blockLoginOnReachingLimit() {
        int slots = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        LoginQueueUsingSemaphore queue = new LoginQueueUsingSemaphore(slots);

        IntStream.range(0, slots).forEach(user -> executorService.execute(queue::tryLogin));
        executorService.shutdown();

        assertEquals(0, queue.availableSlots());
        assertFalse(queue.tryLogin());
    }

    @Test
    public void slotsAvailableWhenLogoutHappens() {
        int slots = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        LoginQueueUsingSemaphore queue = new LoginQueueUsingSemaphore(slots);

        IntStream.range(0, slots).forEach(user -> executorService.execute(queue::tryLogin));
        executorService.shutdown();

        assertEquals(0, queue.availableSlots());
        queue.logout();
        assertEquals(1, queue.availableSlots());
        assertTrue(queue.tryLogin());
    }
}
