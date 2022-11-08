package exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arnab.ray
 * @created on 08/11/22
 */
public class TripleSync {
    private final Object syncObj = new Object();

    void f() {
        synchronized (syncObj) {
            for (int i = 0; i < 5; i++) {
                System.out.println("f()");
                Thread.yield();
            }
        }
    }

    void g() {
        synchronized (syncObj) {
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }

    void h() {
        synchronized (syncObj) {
            for (int i = 0; i < 5; i++) {
                System.out.println("h()");
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        TripleSync ts = new TripleSync();
//        new Thread(ts::f).start();
//        ts.g();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(ts::f);
        ts.g();
        executorService.execute(ts::h);
        executorService.shutdown();
    }
}
