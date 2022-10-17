package woo;
import java.io.Serializable;

public class Transaction implements Serializable {

    /* ATRIBUTES */

    private int _transactionKey;
    private int _cost;
    private boolean _paid;

    /* CONSTRUCTORS */

    public Transaction(int transactionKey,boolean paid) {
        _transactionKey = transactionKey;
        _paid = paid;
    }

    /* GETTERS */

    public int getTransactionKey() { return _transactionKey; }

    public int getCost() { return _cost; }

    public boolean getPaid() { return _paid; }

    /* SETTERS */

    public void setTransactionKey(int transactionKey) { _transactionKey = transactionKey; }

    public void setPaid(boolean paid) { _paid = paid; }

    public void setCost(int cost) { _cost = cost; }

    /* METHODS */

    @Override
    public String toString() {
        return _transactionKey + "|";
    }

}