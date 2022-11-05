package basics;

/**
 * @author arnab.ray
 * @created on 05/11/22
 */
public class MoreBasicThreads {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }

        System.out.println("Waiting for LiftOff!");
    }
}
