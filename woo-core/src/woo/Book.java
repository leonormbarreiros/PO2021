package woo;
import java.io.Serializable;


public class Book extends Product implements Serializable {

    /* ATRIBUTES */
    
    private String _bookTitle;
    private String _bookAuthor;
    private String _isbn;


    /* CONSTRUCTORS */
    public Book(String productKey, Supplier supplier, int productPrice, 
        int stockCriticalValue, String bookTitle, String bookAuthor, String isbn) {

        super(productKey,supplier,productPrice, stockCriticalValue);
        super.setN(3);
        _bookTitle = bookTitle;
        _bookAuthor = bookAuthor;
        _isbn = isbn;
    }

    public Book(String productKey, Supplier supplier, int productPrice, 
        int stockCriticalValue, String bookTitle, String bookAuthor, String isbn, 
        int stock) {

        super(productKey,supplier,productPrice, stockCriticalValue, stock);
        super.setN(3);
        _bookTitle = bookTitle;
        _bookAuthor = bookAuthor;
        _isbn = isbn;
    }

    /* GETTERS */

    public String getBookTitle() { return _bookTitle; }

    public String getBookAuthor() { return _bookAuthor; }

    public String getIsbn() { return _isbn; }


    /* SETTERS */

    public void setBookTitle(String bookTitle) { _bookTitle = bookTitle; }

    public void setBookAuthor(String bookAuthor) { _bookAuthor = bookAuthor; }

    public void setISBN(String isbn) { _isbn = isbn; }


    /* METHODS */

    @Override 
    public String toString() {
        return "BOOK|" + super.toString() +  _bookTitle + "|" + _bookAuthor + "|" +  _isbn;
    }
    
}
