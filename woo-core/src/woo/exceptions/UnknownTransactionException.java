package woo.exceptions;

public class UnknownTransactionException extends Exception {
    private int _transactionKey;

    public UnknownTransactionException(int transactionKey) {
        _transactionKey = transactionKey;
    }
    public int getTransactionKey() {
        return _transactionKey;
    }
}
