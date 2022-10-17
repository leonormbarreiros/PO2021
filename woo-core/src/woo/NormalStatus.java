package woo;
import java.io.Serializable;

/** State pattern for clients: one of the status is normal */
public class NormalStatus extends ClientStatus implements Serializable {
    public NormalStatus(Client c) {
        super(c);
    }

    public double P1(int price) {
        return price * 0.9;
    }
    public double P2(int price, int days) {
        return price;
    }
    public double P3(int price, int days) {
        return price + (price * 0.05 * days);
    }
    public double P4(int price, int days) {
        return price + (price * 0.1 * days);
    }
    public void promote() {
        getClient().setClientStatus( new SelectionStatus(getClient()));
    }
    public void demote() {}
    public void eval(int delay) {}

    public String toString() { return "NORMAL"; }

}
