package woo.exceptions;

public class UnknownLevelException extends Exception {
    private String _serviceLevel;

    public UnknownLevelException(String serviceLevel) {
        _serviceLevel = serviceLevel;
    }
    public String getServiceLevel() {
        return _serviceLevel;
    }
}
