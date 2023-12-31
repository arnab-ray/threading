package examples;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Context {
    private final String userName;

    public Context(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Context{userNameSecret='" + this.userName + "'}";
    }
}

class UserRepository {
    public static Map<Integer, String> users = new ConcurrentHashMap<>();

    public UserRepository() {
//        users = new ConcurrentHashMap<>();
    }

    public String getUserNameForUserId(Integer userId) {
        return users.get(userId);
    }

    public synchronized void addUserName(Integer userId, String userName) {
        users.putIfAbsent(userId, userName);
    }
}

class SharedMapWithUserContext implements Runnable {

    public static Map<Integer, Context> userContextPerUserId = new ConcurrentHashMap<>();
    private final Integer userId;
    private final UserRepository userRepository = new UserRepository();

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContextPerUserId.put(userId, new Context(userName));
    }

    // standard constructor

    public SharedMapWithUserContext(Integer userId) {
        this.userId = userId;
    }
}

class ThreadLocalWithUserContext implements Runnable {

    private static final ThreadLocal<Context> userContext = new ThreadLocal<>();
    private final Integer userId;
    private final UserRepository userRepository = new UserRepository();

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContext.set(new Context(userName));
        System.out.println("thread context for given userId: " + userId + " is: " + userContext.get());
    }

    // standard constructor
    public ThreadLocalWithUserContext(Integer userId) {
        this.userId = userId;
    }
}

public class ThreadLocalDemo {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        userRepository.addUserName(1, "alpha");
        userRepository.addUserName(2, "beta");
        SharedMapWithUserContext firstUser = new SharedMapWithUserContext(1);
        SharedMapWithUserContext secondUser = new SharedMapWithUserContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(SharedMapWithUserContext.userContextPerUserId.size());

        ThreadLocalWithUserContext firstUser_ = new ThreadLocalWithUserContext(1);
        ThreadLocalWithUserContext secondUser_ = new ThreadLocalWithUserContext(2);
        new Thread(firstUser_).start();
        new Thread(secondUser_).start();
    }
}
