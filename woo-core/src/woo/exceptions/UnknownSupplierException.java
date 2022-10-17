package woo.exceptions;

public class UnknownSupplierException extends Exception {
    private String _supplierKey;

    public UnknownSupplierException(String supplierKey) {
        _supplierKey = supplierKey;
    }
    public String getProductKey() {
        return _supplierKey;
    }
}
