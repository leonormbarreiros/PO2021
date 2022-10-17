package woo.exceptions;

public class DuplicateClientException extends Exception {
    private String _clientKey;

    public DuplicateClientException (String clientKey) {
        _clientKey = clientKey;
    }
    public String getClientKey() {
        return _clientKey;
    }
    
}
