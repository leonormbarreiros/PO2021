package woo.exceptions;

public class InactiveSupplierException extends Exception {
    private String _supplierKey;

    public InactiveSupplierException (String supplierKey) {
        _supplierKey = supplierKey;
    }
    public String getClientKey() {
        return _supplierKey;
    }
}