public abstract class Transaction {
    private String transactionId;
    private String date;
    private String description;
    private double amount;

    public Transaction(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.transactionId = generateTransactionId();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    private String generateTransactionId() {
        return "T" + System.currentTimeMillis();  // Simple way to generate a unique ID
    }

    public abstract void displayTransaction();  // Each subclass will define its display method
}
