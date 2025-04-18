public class Income extends Transaction {

    public Income(String description, double amount, String date) {
        super(description, amount, date);
    }

    @Override
    public void displayTransaction() {
        System.out.printf("Income: %s | $%.2f | Date: %s\n", getDescription(), getAmount(), getDate());
    }
}
