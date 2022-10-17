package woo.exceptions;

public class OutOfStockException extends Exception {
    private String _productKey;
    private int _requested;
    private int _available;

    public OutOfStockException(String productKey,int requested, int available) {
        _productKey = productKey;
        _requested = requested;
        _available = available;
    }
    public String getProductKey() {
        return _productKey;
    }
    public int getRequestedAmount() {
        return _requested;
    }
    public int getAvailableAmount() {
        return _available;
    }

}
