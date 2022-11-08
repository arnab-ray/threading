package exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author arnab.ray
 * @created on 08/11/22
 */
public class TripleSyncLock {
    private final Lock lock = new ReentrantLock();

    void f() {
        lock.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("f()");
                Thread.yield();
            }
        } finally {
            lock.unlock();
        }
    }

    void g() {
        lock.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        } finally {
            lock.unlock();
        }
    }

    void h() {
        lock.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("h()");
                Thread.yield();
            }
        } finally {
            lock.unlock();
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
