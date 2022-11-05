import java.util.concurrent.Semaphore;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
public class LoginQueueUsingSemaphore {
    private final Semaphore semaphore;

    public LoginQueueUsingSemaphore(int slotLimits) {
        semaphore = new Semaphore(slotLimits);
    }

    boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    void logout() {
        semaphore.release();
    }

    int availableSlots() {
        return semaphore.availablePermits();
    }
}
