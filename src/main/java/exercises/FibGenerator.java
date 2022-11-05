package exercises;

import java.util.Arrays;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
class FibNum implements Runnable {
    private final int count;
    private final int[] mem;

    public FibNum(int n) {
        count = n;
        mem = new int[n];
    }

    private int fib(int n) {
        if (n < 2)
            return 1;

        if (mem[n] <= 0) {
            mem[n] = fib(n - 1) + fib(n - 2);
        }
        return mem[n];
    }

    @Override
    public void run() {
        int[] arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = fib(i);
        }
        System.out.println(Arrays.toString(arr));
    }
}

public class FibGenerator {
    public static void main(String[] args) {
        new Thread(new FibNum(19)).start();
        new Thread(new FibNum(15)).start();
        new Thread(new FibNum(25)).start();
    }
}
