package woo.exceptions;

public class DuplicateSupplierException extends Exception {
    private String _supplierKey;

    public DuplicateSupplierException (String supplierKey) {
        _supplierKey = supplierKey;
    }
    public String getClientKey() {
        return _supplierKey;
    }
}
