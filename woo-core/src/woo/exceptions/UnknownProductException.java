package woo.exceptions;

public class UnknownProductException extends Exception {
    private String _productKey;

    public UnknownProductException(String productKey) {
        _productKey = productKey;
    }
    public String getProductKey() {
        return _productKey;
    }
}
