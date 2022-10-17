package woo;
import java.io.Serializable;

/** State pattern for clients: one of the status is elite */
public class EliteStatus extends ClientStatus implements Serializable {
    public EliteStatus(Client c) {
        super(c);
    }

    public double P1(int price) {
        return price * 0.9;
    }
    public double P2(int price, int days) {
        return price * 0.9;
    }
    public double P3(int price, int days) {
        return price * 0.95;
    }
    public double P4(int price, int days) {
        return price;
    }
    public void promote() {}
    public void demote() {
        getClient().setClientPoints(getClient().getClientPoints() * 0.25);
        getClient().setClientStatus( new SelectionStatus(getClient()));
    }
    public void eval(int delay) {
        if (delay > 15) {
            demote();
        }
    }
    
    public String toString() { return "ELITE"; }
}
