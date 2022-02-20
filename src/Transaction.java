import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Comparable<Transaction>, Serializable { // Serializable - saving to the disk

    private static int next = 1;
    int transNumber;
    Account acc;
    LocalDate date;
    char operation;
    double amount;

    public Transaction(Account acc, LocalDate date, char operation, double amount) {
        this.acc = acc;
        this.date = date;
        this.operation = operation;
        this.amount = amount;
        transNumber = next++;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.transNumber - o.transNumber;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transNumber=" + transNumber +
                ", acc=" + acc +
                ", date=" + date +
                ", operation=" + operation +
                ", amount=" + amount +
                '}';
    }

    public int getTransNumber() {
        return transNumber;
    }

    public Account getAcc() {
        return acc;
    }

    public LocalDate getDate() {
        return date;
    }

    public char getOperation() {
        return operation;
    }

    public double getAmount() {
        return amount;
    }
}
