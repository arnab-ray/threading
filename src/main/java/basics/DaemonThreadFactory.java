package basics;

import java.util.concurrent.ThreadFactory;

/**
 * @author arnab.ray
 * @created on 06/11/22
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
