package woo;
import java.io.Serializable;
import java.util.ArrayList;


public class Supplier implements Serializable {

    /* ATRIBUTES */

    private String _supplierKey;
    private String _supplierName;
    private String _supplierAddress;
    private boolean _transactions;

    private ArrayList<Integer> _orders = new ArrayList<Integer>();

    /* CONSTRUCTORS */

    public Supplier(String supplierKey, String supplierName, String supplierAddress) {
        _supplierKey = supplierKey;
        _supplierName = supplierName;
        _supplierAddress = supplierAddress;
        _transactions = true;
    }

    /* GETTERS */

    public String getSupplierKey() { return _supplierKey; }

    public String getSupplierName() { return _supplierName; }

    public String getSupplierAddress() { return _supplierAddress; }

    public boolean getSupplierTransactions() { return _transactions; }

    public ArrayList<Integer> getSupplierOrders() { return _orders; }

    /* SETTERS */

    public void setSupplierKey(String supplierKey) { _supplierKey = supplierKey; }

    public void setSupplierName(String supplierName) { _supplierName = supplierName; }

    public void setSupplierAddress(String supplierAddress) { _supplierAddress = supplierAddress; }

    public void setSupplierTransactions(boolean transactions) { _transactions = transactions; }
    
    /* METHODS */

    public void addSupplierOrder(int orderKey) { 
        _orders.add(orderKey); 
    }

    public int getSupplierOrder(int i) {
        return _orders.get(i);
    }

    public void removeSupplierOrder(int orderKey) {
        _orders.remove(orderKey);
    }

    public void clearSupplierOrders() {
        _orders.clear();
    }
    
    public String SupplierActive () {
        if (_transactions == true) {
            return "SIM";
        }
        return "NÃO";
    }
    
    @Override
    public String toString() {
        return _supplierKey + "|" + _supplierName + "|" + _supplierAddress + "|" + SupplierActive();
    }
}