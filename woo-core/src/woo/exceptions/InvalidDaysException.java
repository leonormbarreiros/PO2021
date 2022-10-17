package woo.exceptions;

public class InvalidDaysException extends Exception {
    
    private int _days;

    public InvalidDaysException(int days) {
        _days = days;
    }
    public int getDate() {
        return _days;
    }
}
