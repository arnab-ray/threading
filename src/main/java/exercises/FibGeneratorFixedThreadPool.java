package exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arnab.ray
 * @created on 06/11/22
 */
public class FibGeneratorFixedThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++)
            executorService.execute(new FibNum((i + 50) % 30));
        executorService.shutdown();
    }
}
