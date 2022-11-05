package exercises;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
class Messaging implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;

    public Messaging() {
        System.out.println("Hello! " + id);
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Executing... " + id);
            Thread.yield();
        }

        System.out.println("Bye! " + id);
    }
}

public class MessagingDriver {
    public static void main(String[] args) {
        System.out.println("********* Beginning of main *********");
        for (int i = 0; i < 3; i++) {
            new Thread(new Messaging()).start();
        }
        System.out.println("********* End of main *********");
    }
}
