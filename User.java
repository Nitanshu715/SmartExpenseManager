public class User {
    private String username;
    private double balance;
    private double totalIncome;
    private double totalExpenses;

    // Constructor
    public User(String username, double balance) {
        this.username = username;
        this.balance = balance;
        this.totalIncome = 0;
        this.totalExpenses = 0;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    // Add balance (income or expense)
    public void addBalance(double amount) {
        this.balance += amount;
    }

    // Add income
    public void addIncome(double income) {
        this.totalIncome += income;
        this.addBalance(income);
    }

    // Add expense
    public void addExpense(double expense) {
        this.totalExpenses += expense;
        this.addBalance(-expense);
    }

    // Get total income
    public double getTotalIncome() {
        return totalIncome;
    }

    // Get total expenses
    public double getTotalExpenses() {
        return totalExpenses;
    }
}
