import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USER_DATA_FILE = "users.txt";
    private static Map<String, User> users = new HashMap<>();

    // Load users from the file
    public static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String username = userData[0];
                double balance = Double.parseDouble(userData[1]);

                User user = new User(username, balance);
                users.put(username, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save user data to the file
    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(user.getUsername() + "," + user.getBalance() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the user by username (for login)
    public static User getUser(String username) {
        return users.get(username);
    }

    // Save user data after any modification
    public static void saveUserData(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (User u : users.values()) {
                writer.write(u.getUsername() + "," + u.getBalance() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
