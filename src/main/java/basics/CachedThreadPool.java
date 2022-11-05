package basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            executorService.execute(new LiftOff());
        executorService.shutdown();
    }
}
