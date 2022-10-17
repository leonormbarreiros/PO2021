package woo;
import java.io.Serializable;

/** State pattern for clients: each client has a status and it affects its
relation with the company */

public abstract class ClientStatus implements Serializable {
    private Client _client;
    
    public ClientStatus(Client client) {
        _client = client;
    }

    public Client getClient() { return _client; }
    
    public abstract double P1(int price);
    public abstract double P2(int price, int days);
    public abstract double P3(int price, int days);
    public abstract double P4(int price, int days);
    public abstract void promote();
    public abstract void demote();
    public abstract void eval(int delay);

    @Override
    public abstract String toString();

}
