package examples;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {

    private final List<T> queue = new LinkedList<T>();

    private final int limit = 10;

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        if (queue.isEmpty()) {
            notifyAll();
        }
        queue.add(item);
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        if (queue.size() == limit) {
            notifyAll();
        }
        return queue.remove(0);
    }

}
