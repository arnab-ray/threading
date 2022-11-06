package exercises;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author arnab.ray
 * @created on 06/11/22
 */
public class RandomSleepTask implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;

    @Override
    public void run() {
        int random = new Random().nextInt(10) + 1;
        try {
            TimeUnit.SECONDS.sleep(random);
            System.out.println("(" + id + ")" + " Sleeping for " + random + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            executorService.execute(new RandomSleepTask());
        executorService.shutdown();
    }
}
