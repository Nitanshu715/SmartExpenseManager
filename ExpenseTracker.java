import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseTracker implements Serializable {
    private ArrayList<Transaction> transactions;
    private double budget;
    private double totalIncome;
    private double totalExpenses;

    public ExpenseTracker() {
        this.transactions = new ArrayList<>();
        this.budget = 0;
        this.totalIncome = 0;
        this.totalExpenses = 0;
    }

    // Set the budget, throws an exception if the budget is not valid
    public void setBudget(double budget) throws InvalidAmountException {
        if (budget <= 0) {
            throw new InvalidAmountException("Budget must be greater than zero!");
        }
        this.budget = budget;
        System.out.println("Budget set to: $" + budget);
    }

    // Add income to the tracker
    public void addIncome(Income income) {
        transactions.add(income);
        totalIncome += income.getAmount();
        System.out.println("Income added successfully! Total Balance: $" + getBalance());
    }

    // Add expense to the tracker, throws exception if expense exceeds budget
    public void addExpense(Expense expense) throws BudgetExceededException {
        double updatedExpenses = totalExpenses + expense.getAmount();
        if (budget > 0 && updatedExpenses > budget) {
            throw new BudgetExceededException("Expense exceeds budget!");
        }
        transactions.add(expense);
        totalExpenses += expense.getAmount();
        System.out.println("Expense added successfully! Remaining Balance: $" + getBalance());
    }

    // Display all transactions
    public void displayAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            t.displayTransaction();
        }
        System.out.println("-----------------------------");
        System.out.printf("Total Income: $%.2f | Total Expenses: $%.2f | Remaining Balance: $%.2f\n",
                totalIncome, totalExpenses, getBalance());
    }

    // Get balance (Total income - total expenses)
    public double getBalance() {
        return totalIncome - totalExpenses;
    }

    // Get all transactions
    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }

    // Get category-wise expenses
    public Map<String, Double> getCategoryWiseExpenses() {
        Map<String, Double> categoryExpenses = new HashMap<>();
        for (Transaction t : transactions) {
            if (t instanceof Expense) {
                Expense expense = (Expense) t;
                categoryExpenses.put(expense.getCategory(),
                    categoryExpenses.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
            }
        }
        return categoryExpenses;
    }
}

