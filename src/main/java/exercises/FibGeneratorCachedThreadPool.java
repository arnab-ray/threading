package exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arnab.ray
 * @created on 06/11/22
 */
public class FibGeneratorCachedThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            executorService.execute(new FibNum((i + 50) % 30));
        executorService.shutdown();
    }
}
