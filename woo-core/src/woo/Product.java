package woo;
import java.io.Serializable;

import java.util.LinkedList;

/** Observer pattern for notifications (Products want to notify Clients) */
public abstract class Product implements Serializable {

    /* ATRIBUTES */

    private String _productKey;
    private int _productPrice;
    private int _stockCriticalValue;
    private Supplier _supplier;
    private int _stock;
    private int _n;

    //list of clients that want to be notified about the product
    private LinkedList<Observer> clients = new LinkedList<>();
   
    /* BUILDERS */

    public Product(String productKey, Supplier supplier, int productPrice, int stockCriticalValue) {
        _productPrice = productPrice;
        _productKey = productKey;
        _stockCriticalValue = stockCriticalValue;
        _stock = 0;
        _supplier = supplier;
    }

    public Product(String productKey, Supplier supplier, int productPrice, int stockCriticalValue, int stock) {
        _productPrice = productPrice;
        _productKey = productKey;
        _stockCriticalValue = stockCriticalValue;
        _stock = stock;
        _supplier = supplier;
    }

    /* GETTERS */

    public String getProductKey() { return _productKey; }

    public int getProductPrice() { return _productPrice; }

    public int getStockCriticalValue() { return _stockCriticalValue; }

    public Supplier getSupplier() { return _supplier; }

    public int getStock() { return _stock; }

    public int getN() { return _n; }

    /* SETTERS */

    public void setProductPrice(int productPrice) { _productPrice = productPrice; }

    public void setStockCriticalValue(int stockCriticalValue) { _stockCriticalValue = stockCriticalValue; }

    public void setStock(int stock) { _stock = stock; }

    public void setN(int n) { _n = n; }


    /* METHODS */

    public boolean setProductNotifications(Client c) {
        if (clients.contains(c)) {
            setProductNotificationsOff(c);
            return false;
        }
        setProductNotificationsOn(c);
        return true;
    }

    public void setProductNotificationsOn(Client c) {
        clients.add(c);
    }

    public void setProductNotificationsOff(Client c) {
        clients.remove(c);
    }

    public void BargainNotification() {
        for (Observer c : clients) {
            c.updateAsBargain(_productKey,_productPrice);
        }
    }

    public void NewNotification() {
        for (Observer c : clients) {
            c.updateAsNew(_productKey,_productPrice);
        }
    }

    @Override
    public String toString() {
        return _productKey + "|" + _supplier.getSupplierKey() + "|" + 
            _productPrice + "|" + _stockCriticalValue + "|" + _stock + "|";
    }

}