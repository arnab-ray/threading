package exercises;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author arnab.ray
 * @created on 06/11/22
 */
class FibSum implements Callable<Integer> {
    private final int count;
    private final int[] nums;

    FibSum(int count) {
        this.count = count;
        this.nums = new int[count + 1];
    }

    private int getNthNum(int n) {
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else {
            if (nums[n] <= 0) {
                nums[n] = getNthNum(n - 1) + getNthNum(n - 2);
            }
            return nums[n];
        }
    }

    @Override
    public Integer call() {
        int result = 0;
        for (int i = count; i >= 0; i--)
            result += getNthNum(i);

        return result;
    }
}

public class FibSumGenerator {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<Integer>> result = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            result.add(executorService.submit(new FibSum(i + 3)));

        for (Future<Integer> f : result) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        }
    }
}
