package woo;

/** Observer pattern for notifications (Products want to notify Clients) */
public interface Observer {
    public void updateAsBargain(String productKey,int price);
    public void updateAsNew(String productKey,int price);
}