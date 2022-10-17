package woo;
import java.io.Serializable;

/** State pattern for clients: one of the status is selection */
public class SelectionStatus extends ClientStatus implements Serializable {
    public SelectionStatus(Client c) {
        super(c);
    }

    public double P1(int price) {
        return price * 0.9;
    }
    public double P2(int price, int days) {
        if (days >= 2) {
            return price * 0.95;
        }
        else return price;
    }
    public double P3(int price, int days) {
        if (days > 1) {
            double fine = 0.02 * days;
            return price * (1 + fine);
        }
        else return price;
    }
    public double P4(int price, int days) {
        return price * 1.05 * days;
    }

    public void promote() {
        getClient().setClientStatus( new EliteStatus(getClient()));
    }
    public void demote() {
        getClient().setClientPoints(getClient().getClientPoints() * 0.1);
        getClient().setClientStatus( new NormalStatus(getClient()));
    }
    public void eval(int delay) {
        if (delay > 2) {
            demote();
        }
    }
    public String toString() { return "SELECTION"; }
}
