public class Expense extends Transaction {
    private String category;

    public Expense(String description, double amount, String date, String category) {
        super(description, amount, date);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public void displayTransaction() {
        System.out.printf("Expense: %s | $%.2f | Date: %s | Category: %s\n", getDescription(), getAmount(), getDate(), category);
    }
}
