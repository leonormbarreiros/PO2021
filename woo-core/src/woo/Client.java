package woo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/** Observer pattern for notifications (Clients want to be notified by Products) */
/** State pattern for clients: a client has a status */
public class Client implements Serializable, Observer {

    /* ATRIBUTES */

    private String _clientKey;
    private String _clientName;
    private String _clientAddress;
    private double _points = 0;
    private ClientStatus _status = new NormalStatus(this);
    private int _salesValue;
    private double _paidSalesValue;
    private int _nNotifications = 0;
    private HashMap<Integer, Notification> _notifications = new HashMap<Integer, Notification>();
    private ArrayList<Integer> _sales = new ArrayList<Integer>();

    /* CONSTRUCTORS */

    public Client(String clientKey, String clientName, String clientAddress) {
        _clientKey = clientKey;
        _clientName = clientName;
        _clientAddress = clientAddress;

    }

    /* GETTERS */

    public String getClientKey() { return _clientKey; }

    public String getClientName() { return _clientName; }

    public String getClientAddress() { return _clientAddress; }

    public double getClientPoints() { return _points; }

    public ClientStatus getClientStatus() { return _status; }

    public int getClientSalesValue() { return _salesValue; }

    public double getClientPaidSalesValue() { return _paidSalesValue; }

    public int getClientNNotifications() { return _nNotifications; }

    public HashMap<Integer, Notification> getClientNotifications() { return _notifications; }
    
    public ArrayList<Integer> getClientSales() { return _sales; }
    
    /* SETTERS */

    public void setClientKey(String clientKey) { _clientKey = clientKey; }

    public void setClientName(String clientName) { _clientName = clientName; }

    public void setClientAddress(String clientAddress) { _clientAddress = clientAddress; }

    public void setClientPoints(double points) { _points = points; }

    public void setClientStatus(ClientStatus s) { _status = s; }

    public void setClientSalesValue(int sales) { _salesValue = sales; }

    public void setClientPaidSalesValue(double paidSales) { _paidSalesValue = paidSales; }

    public void setClientNNotifications(int nNotifications) { _nNotifications = nNotifications; }

    public void setClientNotifications(HashMap<Integer, Notification> notifications) { _notifications = notifications; }

    public void setClientSales(ArrayList<Integer> sales) { _sales = sales; } 

    /* METHODS */

    public void addClientSale(int saleKey) { _sales.add(saleKey); }

    public int getClientSale(int i) { return _sales.get(i); }

    public void clearClientSales() { _sales.clear(); }

    public void clearClientNotifications() { _notifications.clear(); }
    
    public void checkDelay(int delay, double cost) {
        _status.eval(delay);
        if (delay <= 0) {
            double points = cost * 10;
            _points += points;
            updateStatus();
        }
    }

    public void updateStatus() {
        if (getClientPoints() > 25000) {
           setClientStatus(new EliteStatus(this));
        } else if (getClientPoints() > 2000) {
            setClientStatus(new SelectionStatus(this));
        }  
    }

    @Override
    public void updateAsBargain(String productKey,int price) {
        NotificationApp notification = new NotificationApp(productKey, "BARGAIN",price);
        _notifications.put(_nNotifications, notification);
        _nNotifications++;
    }

    @Override
    public void updateAsNew(String productKey,int price) {
        NotificationApp notification = new NotificationApp(productKey, "NEW",price);
        _notifications.put(_nNotifications, notification);
        _nNotifications++;
    }

    public String showClientNotifications() {
        String str = "";
        for (int i = 0; i < _nNotifications; i++) {
            Notification n = _notifications.get(i);
            if (n instanceof NotificationApp) {
                NotificationApp not = (NotificationApp) n;
                str += not.display();
            }
            /* if it's another notification type, we would have a different method to 
            display it. Eg.: if it's SMS we would do sms.text(); */
        }
        _notifications.clear();
        return str;
    }

    @Override
    public String toString() {
        return _clientKey + "|" + _clientName + "|" + 
            _clientAddress + "|" +  _status.toString() + "|" + _salesValue + "|" + (int)_paidSalesValue;
    }
}
