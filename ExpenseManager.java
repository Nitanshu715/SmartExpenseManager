import java.util.Scanner;

// Removed package declaration as per your request

public class ExpenseManager {
    private static Scanner scanner = new Scanner(System.in);
    private static User user;

    public static void main(String[] args) {
        // Ensure UserManager and User classes are available
        UserManager.loadUsers(); // Load users before starting

        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        // Log in or create new user
        user = UserManager.getUser(username);
        if (user == null) {
            user = new User(username, 0);  // Create new user if not found
            UserManager.saveUser(user);    // Save new user data
        }

        // Main menu
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWelcome " + user.getUsername() + "!");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Summary");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter income amount: ");
                    double income = scanner.nextDouble();
                    user.addIncome(income);
                    UserManager.saveUserData(user); // Save updated user data
                    System.out.println("Income added.");
                    break;
                case 2:
                    System.out.print("Enter expense amount: ");
                    double expense = scanner.nextDouble();
                    user.addExpense(expense);
                    UserManager.saveUserData(user); // Save updated user data
                    System.out.println("Expense added.");
                    break;
                case 3:
                    System.out.println("Total Income: $" + user.getTotalIncome());
                    System.out.println("Total Expenses: $" + user.getTotalExpenses());
                    System.out.println("Current Balance: $" + user.getBalance());
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}
