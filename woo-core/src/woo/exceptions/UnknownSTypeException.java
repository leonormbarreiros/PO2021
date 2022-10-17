package woo.exceptions;

public class UnknownSTypeException extends Exception {
    private String _serviceType;

    public UnknownSTypeException(String serviceType) {
        _serviceType = serviceType;
    }
    public String getServiceType() {
        return _serviceType;
    }
}
