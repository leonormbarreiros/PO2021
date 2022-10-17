package woo;
import java.io.Serializable;

/** Strategy Pattern for notifications. */
public abstract class Notification implements Serializable {
    
    /* ATRIBUTES */
    private String _productKey;
    private String _description;
    private int _price;
    private DeliveryMethod _deliveryMethod;


    /* CONSTRUCTORS */

    public Notification(String productKey , String description, int price) {
        _productKey = productKey;
        _description = description;
        _price = price;
    }

    /* GETTERS */
    public String getProductKey() { return _productKey; }
    public String getDescription() { return _description; }
    public int getPrice() { return _price; }
    public DeliveryMethod getDeliveryMethod() { return _deliveryMethod; }

    /* SETTERS */

    public void setProductKey(String productKey) { _productKey = productKey; }
    public void setDescription(String description) { _description = description; }
    public void setPrice(int price) { _price = price; }
    public void setDeliveryMethod(DeliveryMethod deliveryMethod) { _deliveryMethod = deliveryMethod; }
    
    /* METHODS */
    
    abstract String display();

    @Override
    public String toString() {
        return  _description + "|" + _productKey + "|" + _price + "\n";
    }

}
