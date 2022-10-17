package woo.exceptions;

public class DuplicateProductException extends Exception {
    private String _productKey;

    public DuplicateProductException (String productKey) {
        _productKey = productKey;
    }
    public String getProductKey() {
        return _productKey;
    }
}
