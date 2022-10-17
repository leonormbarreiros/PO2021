package woo.exceptions;

public class UnrelatedSupplierException extends Exception {
    private String _supplierKey;
    private String _productKey;

    public UnrelatedSupplierException (String supplierKey,String productKey) {
        _supplierKey = supplierKey;
        _productKey = productKey;
    }
    public String getClientKey() {
        return _supplierKey;
    }
    public String getProductKey() {
        return _productKey;
    }
}