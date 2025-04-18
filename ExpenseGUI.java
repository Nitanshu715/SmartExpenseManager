import java.awt.*;
import java.util.Map;
import javax.swing.*;
import java.io.FileWriter;

public class ExpenseGUI {
    private ExpenseTracker tracker = new ExpenseTracker();
    private String currentUsername = "";
    private final String USER_LOG_FILE = "users.txt";

    // Main method to launch the login screen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseGUI().createLoginScreen());
    }

    // Method to create and show the login screen
    public void createLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                loginFrame.dispose(); // Close the login window
                createAndShowGUI(); // Show the main dashboard
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials, try again.");
            }
        });

        loginFrame.setVisible(true);
    }

    // Allow any username & password; store current username
    private boolean validateLogin(String username, String password) {
        this.currentUsername = username; // Remember user
        return true;
    }

    // Log every income/expense to users.txt
    private void logTransaction(String type, String description, double amount, String date, String category) {
        try {
            FileWriter writer = new FileWriter(USER_LOG_FILE, true);
            String log = "Username: " + currentUsername + " | Type: " + type +
                         " | Desc: " + description + " | Amount: " + amount + " | Date: " + date;
            if (category != null && !category.isEmpty()) {
                log += " | Category: " + category;
            }
            writer.write(log + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    // Method to create and show the main dashboard GUI
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Smart Expense Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(6, 1));

        JButton setBudgetBtn = new JButton("Set Budget");
        JButton addIncomeBtn = new JButton("Add Income");
        JButton addExpenseBtn = new JButton("Add Expense");
        JButton showTransBtn = new JButton("Show Transactions");
        JButton showBalanceBtn = new JButton("Show Balance");
        JButton showCategoryBtn = new JButton("Category-wise Expenses");

        frame.add(setBudgetBtn);
        frame.add(addIncomeBtn);
        frame.add(addExpenseBtn);
        frame.add(showTransBtn);
        frame.add(showBalanceBtn);
        frame.add(showCategoryBtn);

        setBudgetBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter budget:");
            try {
                double budget = Double.parseDouble(input);
                tracker.setBudget(budget);
                JOptionPane.showMessageDialog(null, "Budget set successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
            }
        });

        addIncomeBtn.addActionListener(e -> {
            try {
                String desc = JOptionPane.showInputDialog("Description:");
                double amt = Double.parseDouble(JOptionPane.showInputDialog("Amount:"));
                String date = JOptionPane.showInputDialog("Date (MM-DD-YYYY):");

                tracker.addIncome(new Income(desc, amt, date));
                logTransaction("Income", desc, amt, date, null);
                JOptionPane.showMessageDialog(null, "Income added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        addExpenseBtn.addActionListener(e -> {
            try {
                String desc = JOptionPane.showInputDialog("Description:");
                double amt = Double.parseDouble(JOptionPane.showInputDialog("Amount:"));
                String date = JOptionPane.showInputDialog("Date (MM-DD-YYYY):");
                String cat = JOptionPane.showInputDialog("Category:");

                int option = JOptionPane.showConfirmDialog(null, "Do you want to split this expense with friends?", "Split Expense", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    int people = Integer.parseInt(JOptionPane.showInputDialog("Enter number of people (including yourself):"));

                    if (people <= 0) {
                        JOptionPane.showMessageDialog(null, "Number of people must be greater than zero.");
                        return;
                    }

                    double splitAmount = amt / people;
                    tracker.addExpense(new Expense(desc + " (Split)", splitAmount, date, cat));
                    logTransaction("Expense", desc + " (Split)", splitAmount, date, cat);
                    JOptionPane.showMessageDialog(null, "Expense split among " + people + " people. Your share $" + splitAmount + " added.");
                } else {
                    tracker.addExpense(new Expense(desc, amt, date, cat));
                    logTransaction("Expense", desc, amt, date, cat);
                    JOptionPane.showMessageDialog(null, "Expense added!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        showTransBtn.addActionListener(e -> {
            JTextArea area = new JTextArea();
            for (Transaction t : tracker.getAllTransactions()) {
                area.append(t.getTransactionId() + " | " + t.getDate() + " | " + t.getDescription() + " | $" + t.getAmount() + "\n");
            }
            JOptionPane.showMessageDialog(null, new JScrollPane(area));
        });

        showBalanceBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Balance: $" + tracker.getBalance());
        });

        showCategoryBtn.addActionListener(e -> {
            StringBuilder msg = new StringBuilder();
            for (Map.Entry<String, Double> entry : tracker.getCategoryWiseExpenses().entrySet()) {
                msg.append(entry.getKey()).append(" : $").append(entry.getValue()).append("\n");
            }
            JOptionPane.showMessageDialog(null, msg.toString());
        });

        frame.setVisible(true);
    }
}
