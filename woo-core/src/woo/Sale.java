package woo;
import java.io.Serializable;

public class Sale extends Transaction implements Serializable {

    /* ATRIBUTES */

    private Client _client;
    private Product _product;
    private int _date;
    private int _paymentDeadline;
    private int _amount;
    private double _actualcost;

    /* CONSTRUCTORS */

    public Sale(int transactionKey, Client client,Product product,
    int amount,int cost, int paymentDeadline, int date) {
        super(transactionKey,false);
        super.setCost(cost);
        _client = client;
        _product = product;
        _actualcost = cost;
        _amount = amount;
        _date = date;
        _paymentDeadline = paymentDeadline;
    }

    /* GETTERS */

    public Client getSaleClient() { return _client; }

    public Product getSaleProduct() { return _product; }

    public int getSaleDate() { return _date; }

    public int getSalePaymentDeadline() { return _paymentDeadline; }

    public int getSaleAmount() { return _amount; }

    public double getSaleActualCost() { return _actualcost; }

    /* SETTERS */

    public void setSaleClient(Client client) { _client = client; }

    public void setSaleProduct(Product product) { _product = product; }
    
    public void setSaleDate(int date) { _date = date; }

    public void setSalePaymentDeadline(int paymentDeadline) { _paymentDeadline = paymentDeadline; }

    public void setSaleAmount(int amount) { _amount = amount; }

    public void setSaleActualCost(double cost) { _actualcost = cost; }
    
    /* METHODS */

    public void updateSaleActualCost(int date) {
        int n = _product.getN();
        if ((_paymentDeadline - date) >= n) {
            _actualcost = _client.getClientStatus().P1(super.getCost());
        } else if ((0 <= (_paymentDeadline - date)) && ((_paymentDeadline - date) < n)) {
            _actualcost = _client.getClientStatus().P2(super.getCost(), _paymentDeadline - date);
        } else if ((0 < (date - _paymentDeadline)) && ((date - _paymentDeadline) <= n)) {
            _actualcost = _client.getClientStatus().P3(super.getCost(), date - _paymentDeadline);
        } else if ((date - _paymentDeadline) > n) {
            _actualcost = _client.getClientStatus().P4(super.getCost(), date - _paymentDeadline);
        }
    }

    @Override
    public String toString() {
        if (super.getPaid()== true) {
            return super.toString() + _client.getClientKey() + "|" + _product.getProductKey() +
             "|" +_amount + "|" + super.getCost() + "|" + (int)_actualcost + "|" + _paymentDeadline  + "|" + _date + '\n';
        } else {
            return super.toString() + _client.getClientKey() + "|" + _product.getProductKey() + "|" +
        _amount + "|" + super.getCost()+ "|" + (int)_actualcost + "|" + _paymentDeadline + '\n';
        }  
    }

}