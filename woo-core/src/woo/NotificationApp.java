package woo;
import java.io.Serializable;

/** Strategy Pattern for notifications. 
 * The delivery strategy by default is the app notification.
*/
public class NotificationApp extends Notification implements Serializable {
   
    /* CONSTRUCTORS */

    public NotificationApp(String productKey, String description, int price) {
        super(productKey, description, price);
        super.setDeliveryMethod(new AppDelivery());
    }

    /* METHODS */
    
    public String display() {
        return notificate();
    }
    public String notificate() {
        return super.toString();
    }
}
