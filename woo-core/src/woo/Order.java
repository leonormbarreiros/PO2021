package woo;
import java.io.Serializable;
import java.util.LinkedHashMap;

public class Order extends Transaction implements Serializable {

    /* ATRIBUTES */

    private Supplier _supplier;
    private int _date;
    private LinkedHashMap<String,Integer> _products = new LinkedHashMap<String,Integer>();

    /* CONSTRUCTORS */

    public Order(int transactionKey,Supplier supplier,int cost, int date) {
        super(transactionKey,true);
        super.setCost(cost);
        _supplier = supplier;
        _date = date;
    }

    /* GETTERS */

    public Supplier getOrderSupplier() { return _supplier; }

    public int getOrderDate() { return _date; }

    public LinkedHashMap<String,Integer> getOrderProducts() { return _products; }


    /* SETTERS */

    public void setOrderSupplier(Supplier supplier) { _supplier = supplier; }

    public void setOrderDate(int date) { _date = date; }

    public void setOrderProducts(LinkedHashMap<String,Integer> products) { _products = products; }

    /* METHODS */

    public void addProductOrder(String productKey,int amount) {
        _products.put(productKey,amount);
    }
    public void removeProductOrder(String productKey) {
        _products.remove(productKey);
    }
    public void clearProductOrder() {_products.clear(); }

    @Override
    public String toString() {
        String str = "";
        str += super.toString() + _supplier.getSupplierKey() + "|" + super.getCost() +  "|" + _date + '\n';
        for (String p : _products.keySet()) {
            str += p + "|" + _products.get(p) + '\n';
        }
        return str;
    }
}