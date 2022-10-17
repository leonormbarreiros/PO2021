package woo;
import java.io.Serializable;


public class Box extends Product implements Serializable {

    /* ATRIBUTES */
    
    private ServiceType _serviceType;

    /* CONSTRUCTORS */

    public Box(String productKey,int productPrice, int stockCriticalValue,
        Supplier supplier, String serviceType) {

        super(productKey, supplier, productPrice, stockCriticalValue);
        super.setN(5);
        _serviceType = ServiceType.valueOf(serviceType);
    }

    public Box(String productKey,int productPrice, int stockCriticalValue,
        Supplier supplier, String serviceType, int stock) {

        super(productKey, supplier, productPrice, stockCriticalValue, stock);
        super.setN(5);
        _serviceType = ServiceType.valueOf(serviceType);
    }

    /* GETTERS */

    public ServiceType getServiceType() { return _serviceType; }

    /* SETTERS */

    public void setServiceType(ServiceType serviceType) { _serviceType = serviceType; }

    /* METHODS */

    public String infoBox() {
        return super.toString() + _serviceType.toString();
    }

    @Override
    public String toString() {
        return "BOX|" + super.toString() + _serviceType.toString();
    }
    
}