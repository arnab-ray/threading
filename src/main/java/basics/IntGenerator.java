package basics;

/**
 * @author arnab.ray
 * @created on 07/11/22
 */
abstract class IntGenerator {
    private volatile boolean cancelled = false;
    public abstract int next();
    public void cancel() {
        cancelled = true;
    }
    public boolean isCancelled() {
        return cancelled;
    }
}
