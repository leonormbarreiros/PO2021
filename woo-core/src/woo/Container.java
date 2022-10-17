package woo;
import java.io.Serializable;


public class Container extends Box implements Serializable {

    /* ATRIBUTES */

    private ServiceLevel _serviceLevel;

    /* CONSTRUCTORS */

    public Container(String productKey,int productPrice, int stockCriticalValue,
    Supplier supplier, String serviceType, String serviceLevel) {

        super(productKey, productPrice, stockCriticalValue, supplier, serviceType);
        super.setN(8);
        _serviceLevel = ServiceLevel.valueOf(serviceLevel);
    }

    public Container(String productKey,int productPrice, int stockCriticalValue,
    Supplier supplier, String serviceType, String serviceLevel, int stock) {
        super(productKey, productPrice, stockCriticalValue, supplier, serviceType, stock);
        super.setN(8);
        _serviceLevel = ServiceLevel.valueOf(serviceLevel);
    }

    /* GETTERS */

    public ServiceLevel getServiceLevel() { return _serviceLevel; }


    /* SETTERS */

    public void setServiceLevel(ServiceLevel serviceLevel) { _serviceLevel = serviceLevel; }

    /* METHODS */

    @Override
    public String toString() {
        return "CONTAINER|" + super.infoBox() + "|" + _serviceLevel.toString();
    }
}