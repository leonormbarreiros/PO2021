package woo.exceptions;

public class UnknownClientException extends Exception {
    private String _clientKey;

    public UnknownClientException(String clientKey) {
        _clientKey = clientKey;
    }
    public String getClientKey() {
        return _clientKey;
    }
}
