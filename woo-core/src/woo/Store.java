package woo;

import woo.exceptions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;
  /** TreeMap initialization. **/
  /** Gathers clients, products and suppliers (separately). **/
  private TreeMap<String, Client> _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);
  private TreeMap<String, Product> _products = new TreeMap<String, Product>(String.CASE_INSENSITIVE_ORDER);
  private TreeMap<String, Supplier> _suppliers = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);
  private HashMap<Integer,Transaction> _transactions = new HashMap<Integer,Transaction>();
  /** Current system date. **/
  private int _date = 0;
  /** Keeps track of transaction keys **/
  private int transactionKey = 0;
  /** Total value of orders made */
  private int _orders = 0;
  /** Total value of sales made */
  private int _sales = 0;
  /** Total value of paid sales made */
  private int _paidSales = 0;

  /*-----------------------------------------MAIN------------------------------*/

  /*----- commands -----*/

  /**
   * Returns the companies available balance (paid sales - orders)
   */
  public int getAvailableBalance() {
    return _paidSales - _orders;
  }

  /**
   * Returns the companies accounting balance (sales - orders)
   */
  public int getAccountingBalance() {
    updateSales();
    return _sales - _orders;
  }

  /*----- auxiliaries -----*/
  
  /**
   * Updates the actual cost for all (not paid) sales
   */
  public void updateSales() {
    _sales = 0;
    /* we go through each client and all sales by each of them because sales relate 
    to clients; so we don't have to look at any orders -> we look at fewer objects 
    this way */
    for (String clientKey : _clients.keySet()) {
      for (int saleKey : getClient(clientKey).getClientSales()) {
        Transaction t = getTransaction(saleKey);
        Sale s = (Sale) t;
        if (s.getPaid() == false) {
          s.updateSaleActualCost(getCurrentDate());
        } 
        _sales += s.getSaleActualCost();
      }
    }
  }

  /*-----------------------------------------DATE------------------------------*/

  /*----- commands -----*/

  /**
   * Returns the current system date (getter). 
   */
  public int getCurrentDate() {
    return _date;
  }

  /**
   * Increases the current system date in the number of days specified.
   * @param days
   * @throws InvalidDaysException if days to advance are invalid (negative)
   */
  public void advanceCurrentDate(int days) throws InvalidDaysException {

    // the number os days to advance needs to be positive
    if (days < 0) {
      throw new InvalidDaysException(days);
    }
    _date += days;
  }

  

  /*----------------------------------------CLIENTS----------------------------*/

  /*----- commands -----*/

  /**
   * Registers a new client given its atributes.
   * @param clientKey the client's id
   * @param clientName the client's name
   * @param clientAddress the client's address
   * @throws DuplicateClientException if a client with the same id already exists
   */
  public void registerClient(String clientKey, String clientName, 
    String clientAddress) throws DuplicateClientException {

    Client aux = searchClient(clientKey);
    if (aux != null) {
      throw new DuplicateClientException(clientKey);
    }

    // create the object
    Client c = new Client(clientKey, clientName, clientAddress);
    // put it in the map
    _clients.put(c.getClientKey(), c);

    for (Product p: _products.values()) {
      p.setProductNotificationsOn(c);
    }
  }

  /**
   * Returns (a String containing) the client corresponding to the given id
   * @param clientKey the client's id
   * @throws UnknownClientException if the client with the id doesn't exist
   */
  public String showClient(String clientKey) throws UnknownClientException {
    Client c = searchClient(clientKey);
    if (c == null) {
      throw new UnknownClientException(clientKey);
    }

    return c.toString() + "\n" + c.showClientNotifications();
  }

  /**
   * Returns (a String containing) all clients.
   */
  public String showAllClients() {
    String str = "";

    // get each client and add to result
    for (String s : _clients.keySet()) {
      str = str + getClient(s).toString() + "\n";
    }
    return str;
  }

  /**
   * Switches the notifications for a certain product, for that client
   * @param clientKey the client's id
   * @param productKey the product's id
   * @throws UnknownClientException if the client doesn't exist
   * @throws UnknownProductException if the product doesn't exist
   */
  public boolean toggleProductNotifications(String clientKey,String productKey) 
    throws UnknownClientException, UnknownProductException {

    Client c = searchClient(clientKey);
    if (c == null) {
      throw new UnknownClientException(clientKey);
    }
    Product p = searchProduct(productKey);
    if (p == null) {
      throw new UnknownProductException(productKey);
    }

    return p.setProductNotifications(c);
  }

  /**
   * Returns (a String containing) the transactions by a client
   * @param clientKey the client's id
   * @throws UnknownClientException if the client doesn't exist
   */
  public String showClientTransactions(String clientKey) throws UnknownClientException {
    String str = "";

    Client c = searchClient(clientKey);
    if (c == null) {
      throw new UnknownClientException(clientKey);
    }
    for (int saleKey = 0; saleKey < c.getClientSales().size(); saleKey++) {
      Transaction t = searchTransaction(c.getClientSale(saleKey));
      Sale s = (Sale) t;
      if (s.getPaid() == false) {
        /* if the sale isn't paid update its actual cost */
        s.updateSaleActualCost(getCurrentDate());
      }
      str = str + s.toString() + '\n';
    }
    return str;
  }


  /*----- auxiliaries -----*/

  /**
   * Returns a specific client.
   * @param clientKey the client's id
   */
  public Client getClient(String clientKey)  {
    // we can assume the client exists in the map, just search
    Client c = _clients.get(clientKey);
    return c;
  }
 
  /**
   * Searches for a specific client.
   * @param clientKey the client's id
   */
  public Client searchClient(String clientKey) {
    Client c = null;
    if (_clients.containsKey(clientKey)) {
      // search
      c = _clients.get(clientKey);
    }
    return c;
  }

  /*-----------------------------------------PRODUCTS--------------------------*/

  /*----- commands -----*/

  /**
   * Returns (a String containing) all products.
   */
  public String showAllProducts() {
    String str = "";

    // get each product and add to result
    for (String s : _products.keySet()) {
      str = str + getProduct(s).toString() + "\n";
    }
    return str;
  }

  /**
   * Changes the price of the product with the specified id.
   * @param productKey the product's id
   * @param price the product's new price
   * @throws UnknownProductException if the product with the id doesn't exist
   */
  public void changePrice(String productKey, int price) throws UnknownProductException {
    Product p = searchProduct(productKey);
    if (p == null) {
      throw new UnknownProductException(productKey);
    }
    int old_price = p.getProductPrice();

    if (price <= 0) return;

    p.setProductPrice(price);
    
    //product is cheaper
    if (price < old_price) {
      p.BargainNotification();
    }
  }

  /**
   * Registers a new box given its atributes.
   * @param productKey the product's id (a box is a product)
   * @param productPrice the product's price (a box is a product)
   * @param stockCriticalValue the product stock's critical value (a box is a product)
   * @param supplierKey the product supplier's key (a box is a product)
   * @param serviceType the box's service type
   * @throws DuplicateProductException if a product with the id already exists
   * @throws UnknownSupplierException if the supplier with the key doesn't exist
   * @throws UnknownSTypeException if the service type doens't exist
   */
  public void registerProductBox(String productKey,int productPrice, int stockCriticalValue,
  String supplierKey, String serviceType) 
  throws DuplicateProductException, UnknownSupplierException, UnknownSTypeException {
    
    Product p = searchProduct(productKey);
    if (p != null) {
      throw new DuplicateProductException(productKey);
    }
    
    Supplier s = searchSupplier(supplierKey);
    if (s == null) {
      throw new UnknownSupplierException(supplierKey);
    }

    if (!serviceType.equals("NORMAL") && !serviceType.equals("AIR") && !serviceType.equals("EXPRESS") && !serviceType.equals("PERSONAL")) {
      throw new UnknownSTypeException(serviceType);
    }

    if (productPrice <= 0 || stockCriticalValue < 0) return;

    Box b = new Box(productKey, productPrice, stockCriticalValue, s, serviceType);

    _products.put(b.getProductKey(), b);

    for (String i : _clients.keySet()) {
      b.setProductNotificationsOn(_clients.get(i));
    }
  }

  /**
   * Registers a new container given its atributes.
   * @param productKey the product's id (a container is a product)
   * @param productPrice the product's price (a container is a product)
   * @param stockCriticalValue the product stock's critical value (a container is a product)
   * @param supplierKey the product supplier's key (a container is a product)
   * @param serviceType the box's service type (a container is a box)
   * @param serviceLevel the container's serv_supplierKeyice level
   * @throws DuplicateProductException if a product with the id already exists
   * @throws UnknownSupplierException if the supplier with the key doesn't exist
   * @throws UnknownSTypeException if the service type doens't exist
   * @throws UnknownLevelException if the service level doesn't exist
   */
  public void registerProductContainer(String productKey,int productPrice, int stockCriticalValue,
  String supplierKey, String serviceType, String serviceLevel) 
  throws DuplicateProductException, UnknownSupplierException, UnknownSTypeException, UnknownLevelException {

    Product p = searchProduct(productKey);
    if (p != null) {
      throw new DuplicateProductException(productKey);
    }
    
    Supplier s = searchSupplier(supplierKey);
    if (s == null) {
      throw new UnknownSupplierException(supplierKey);
    }

    if (!serviceType.equals("NORMAL") && !serviceType.equals("AIR") && !serviceType.equals("EXPRESS") && !serviceType.equals("PERSONAL")) {
      throw new UnknownSTypeException(serviceType);
    }

    if (!serviceLevel.equals("B4") && !serviceLevel.equals("C4") && !serviceLevel.equals("C5") && !serviceLevel.equals("DL")) {
      throw new UnknownLevelException(serviceLevel);
    }

    if (productPrice <= 0 || stockCriticalValue < 0) return;

    Container c = new Container(productKey, productPrice, stockCriticalValue, s, serviceType, serviceLevel);

    _products.put(c.getProductKey(), c);

    for (String i : _clients.keySet()) {
      c.setProductNotificationsOn(_clients.get(i));
    }
  }

  /**
   * Registers a new book given its atributes,
   * @param productKey the product's id (a book is a product)
   * @param bookTitle the book's title
   * @param bookAuthor the book's author
   * @param isbn the book's isbn
   * @param productPrice the product's price (a book is a product)
   * @param stockCriticalValue the product stock's critical value (a book is a product)
   * @param supplierKey the product supplier's key (a book is a product)
   * @throws DuplicateProductException if a product with the id already exists
   * @throws UnknownSupplierException if the supplier with the key doesn't exist
   */
  public void registerProductBook(String productKey, String bookTitle, String bookAuthor, 
  String isbn, int productPrice, int stockCriticalValue, String supplierKey) 
  throws DuplicateProductException, UnknownSupplierException {

    Product p = searchProduct(productKey);
    if (p != null) {
      throw new DuplicateProductException(productKey);
    }
    
    Supplier s = searchSupplier(supplierKey);
    if (s == null) {
      throw new UnknownSupplierException(supplierKey);
    }

    if (productPrice <= 0 || stockCriticalValue < 0) return;

    Book b = new Book(productKey, s, productPrice, stockCriticalValue, bookTitle, bookAuthor, isbn);

    _products.put(b.getProductKey(), b);

    for (String i : _clients.keySet()) {
      b.setProductNotificationsOn(_clients.get(i));
    }
  }

  /*----- auxiliaries -----*/

  /**
   * Returns a specific product.
   * @param productKey the product's id.
   */
  public Product getProduct(String productKey) {
    // we can assume the product exists in the map, just search
    Product p = _products.get(productKey);
    return p;
  }

  /**
   * Searches for a specific product.
   * @param productKey the product's id.
   */
  public Product searchProduct(String productKey) {
    Product p = null;
    if (_products.containsKey(productKey)) {
      // search
      p = _products.get(productKey);
    }
    return p;
  }


  /*------------------------------------------SUPPLIERS------------------------*/

  /*----- commands -----*/

  /**
   * Registers a new supplier.
   * @param supplierKey the supplier's id
   * @param supplierName the supplier's name
   * @param supplierAddress the supplier's address
   * @throws DuplicateSupplierException if a supplier with the id already exists
   */
  public void registerSupplier(String supplierKey,String supplierName,String supplierAddress) 
    throws DuplicateSupplierException {

    Supplier aux = searchSupplier(supplierKey);
    if (aux != null) {
      throw new DuplicateSupplierException(supplierKey);
    }

    Supplier s = new Supplier(supplierKey, supplierName, supplierAddress);
    _suppliers.put(s.getSupplierKey(), s);
  }

  /**
   * Returns (a String containing) all suppliers.
   */
  public String showAllSuppliers() {
    String str ="";

    // get each supplier and add to result
    for (String s: _suppliers.keySet()) {
      str = str + getSupplier(s).toString() + "\n";
    }
    return str;
  }

  /**
   * Switches the transactions of a supplier.
   * @param supplierKey the supplier's id (whose transactions we're toggling)
   * @throws UnknownSupplierException if the supplier with the id doens't exist
   */
  public boolean toggleTransactions(String supplierKey) throws UnknownSupplierException {
    Supplier s = searchSupplier(supplierKey);
    if (s == null) {
      throw new UnknownSupplierException(supplierKey);
    }
    s.setSupplierTransactions(!s.getSupplierTransactions());
    return s.getSupplierTransactions();
  }

  /**
   * Returns (a String containing) all transactions by a supplier
   * @param supplierKey the supplier's id
   * @throws UnknownSupplierException if the supplier doesn't exist
   */
  public String showSupplierTransactions(String supplierKey) throws UnknownSupplierException {
    String str = "";

    Supplier s = searchSupplier(supplierKey);

    if (s == null) {
      throw new UnknownSupplierException(supplierKey);
    }
    for (int orderKey = 0; orderKey < s.getSupplierOrders().size(); orderKey++) {
      Transaction t = searchTransaction(s.getSupplierOrder(orderKey));
      str = str + t.toString();
    }
  
    return str;
  }

  /*----- auxiliaries -----*/
  
  /**
   * Returns a specific supplier.
   * @param supplierKey the supplier's id
   */
  public Supplier getSupplier(String supplierKey) {
    Supplier s = _suppliers.get(supplierKey);
    return s;
  }

  /**
   * Searches for a supplier
   * @param supplierKey the supplier's id
   */
  public Supplier searchSupplier(String supplierKey) {
    Supplier s = null;
    if (_suppliers.containsKey(supplierKey)) {
      // search
      s = _suppliers.get(supplierKey);
    }
    return s;
  }

  /*--------------------------------- TRANSACTIONS-----------------------------*/

  /*----- commands -----*/

  /**
   * Returns (a String containing) a transaction
   * @param transactionKey the transaction's id
   * @throws UnknownTransactionException if the transaction doesn't exist
   */
  public String showTransaction(int transactionKey) throws UnknownTransactionException {
    Transaction t = searchTransaction(transactionKey);
    if (t == null) {
      throw new UnknownTransactionException(transactionKey);
    }
    if (t.getPaid() == false) {
      /* if it's not paid we know it's a sale and we need to apdate its actual cost */
      Sale s = (Sale) t;
      s.updateSaleActualCost(getCurrentDate());
      return s.toString();
    }
    return t.toString();
  }


  /**
   * Registers a new order.
   * @param supplierKey the supplier's id (who we're ordering from)
   * @param products the products ordered (each key and amount)
   * @throws InactiveSupplierException if the supplier doesn't have active transactions
   * @throws UnrelatedSupplierException if the supplier doesn't supply one of the products
   * @throws UnknownSupplierException if the supplier doesn't exist
   * @throws UnknownProductException if one of the products doesn't exist
   */
  public void registerOrderTransaction(String supplierKey, LinkedHashMap<String,Integer> products) 
    throws InactiveSupplierException, UnrelatedSupplierException, UnknownSupplierException,UnknownProductException {
    
    Supplier s = getSupplier(supplierKey);

    //If Supplier does not exist
    if (s == null) {
      throw new UnknownSupplierException(supplierKey);
    }
    //If supplier isn't allowed to have transactions
    if (s.getSupplierTransactions() == false) {
      throw new InactiveSupplierException(supplierKey);
    }

    for (String productKey : products.keySet()) {
      Product p = searchProduct(productKey);
      //If product does not exist
      if (p == null) {
        throw new UnknownProductException(productKey);
      }
      //If product doesn't belong to supplier
      if (!p.getSupplier().getSupplierKey().equalsIgnoreCase(supplierKey)) {
        throw new UnrelatedSupplierException(supplierKey,productKey) ;
      }

      if (products.get(productKey) <= 0) continue;
    }

    Order o = new Order(transactionKey,s,getOrderPrice(products),getCurrentDate());

    for (String productKey : products.keySet()) {
      Product p = getProduct(productKey);

      p.setStock(getProduct(productKey).getStock() + products.get(productKey));
      if (p.getStock() == 0) {
        p.NewNotification();
      }

      o.addProductOrder(getProduct(productKey).getProductKey(), products.get(productKey));
    }

    s.addSupplierOrder(transactionKey);
    _transactions.put(transactionKey,o);
    transactionKey++;

    _orders += o.getCost();
  }

  /**
   * Registers a new sale
   * @param clientKey the client's id (who is buying)
   * @param paymentDeadline the limit for them to pay
   * @param productKey the product's id (what the client is buying)
   * @param amount the units they're buying
   * @throws OutOfStockException if there's not enough product
   * @throws UnknownProductException if the product doesn't exist
   * @throws UnknownClientException if the client doesn't exist
   */
  public void registerSaleTransaction(String clientKey,int paymentDeadline,String productKey,int amount)
  throws OutOfStockException, UnknownProductException, UnknownClientException {
    
    Client c = searchClient(clientKey);
    //if client does not exist
    if (c == null) {
      throw new UnknownClientException(clientKey);
    }
    Product p = searchProduct(productKey);
    //if product does not exist
    if (p == null) {
      throw new UnknownProductException(productKey);
    }
    //if there's not enough stock
    if (p.getStock() < amount) {
      throw new OutOfStockException(productKey,amount,p.getStock());
    }
    if (paymentDeadline < 0 || amount <= 0) return;

    int price = p.getProductPrice() * amount;

    Sale s = new Sale(transactionKey,c, p,amount,price,paymentDeadline,getCurrentDate());

    _transactions.put(transactionKey,s);

    //Updates the stock
    p.setStock(getProduct(productKey).getStock() - amount);

    //Updates client sales
    c.setClientSalesValue(getClient(clientKey).getClientSalesValue() + price); 
    c.addClientSale(transactionKey);

    transactionKey++;

  }

  /**
   * Pay for a certain transaction.
   * @param transactionKey the transaction's id
   * @throws UnknownTransactionException if the transaction doesn't exist
   */
  public void pay(int transactionKey) throws UnknownTransactionException {
    
    Transaction t = searchTransaction(transactionKey); // search

    if (t == null) {
      throw new UnknownTransactionException(transactionKey);
    }
    if (t.getPaid() == true) return; // if it's already paid do nothing

    Sale s = (Sale) t; // the transaction is a sale
    Client c = s.getSaleClient();

    /* payment period verification */
    s.updateSaleActualCost(getCurrentDate());
    c.checkDelay(getCurrentDate() - s.getSalePaymentDeadline(), s.getSaleActualCost());

    /* update sale values */
    s.setPaid(true); // sale paid (a sale is a transaction)
    s.setSaleDate(getCurrentDate()); // date it was paid

    c.setClientPaidSalesValue(c.getClientPaidSalesValue() + s.getSaleActualCost() );

    _paidSales += s.getSaleActualCost();
  }

  /*----- auxiliaries -----*/
  
  /**
   * Gets a registered transaction.
   * @param transactionKey the transaction's id
   */
  public Transaction getTransaction(int transactionKey) {
    Transaction t = _transactions.get(transactionKey);
    return t;
  }

  /**
   * Looks for a transaction given its key.
   * @param transactionKey the transaction's id
   */
  public Transaction searchTransaction(int transactionKey) {
    Transaction t = null;
    if (_transactions.containsKey(transactionKey)) {
      // search
      t = _transactions.get(transactionKey);
    }
    return t;
  }

  /**
   * Calculates de price of an order
   * @param products the keys and amounts of each product in the order
   */
  public int getOrderPrice(LinkedHashMap<String,Integer> products) {
    int cost = 0;
    for (String s : products.keySet()) {
      cost += getProduct(s).getProductPrice()*products.get(s);
    }
    return cost;
  }

  /*-------------------------------------------LOOKUPS-------------------------*/

  /**
   * Returns (a String containing) the products with a price lower than the limit.
   * @param priceLimit the limit for the price
   */
  public String lookupProductsUnderTopPrice(int priceLimit) {
    String str ="";

    for (String s: _products.keySet()) {
      Product p = getProduct(s);
      if (p.getProductPrice() < priceLimit) {
        str = str + p.toString() + "\n";
      }
    }
    return str;
  }

  /**
   * Returns (a String containing) the payed transactions of a client
   * @param clientKey the client's id
   * @throws UnknownClientException if the client doesn't exist
   */
  public String lookupPaymentsByClient(String clientKey) throws UnknownClientException {
    String str ="";
    Client c = searchClient(clientKey);
    if (c == null) {
      throw new UnknownClientException(clientKey);
    }

    for (int i = 0; i < c.getClientSales().size(); i++) {
      int transactionKey = c.getClientSales().get(i);
      // add each paid transaction (every client transaction is a Sale)
      Transaction t = getTransaction(transactionKey);
      if (t.getPaid() == true) {
        str = str + t.toString() + "\n";
      }
    }

    return str;
  }

  /*--------------------------------------READING FROM FILE--------------------*/

  /**
   * Transforms a vector of Strings into objects.
   * @param fields the different atributes we're filtering
   */
  void registerFromFields(String[] fields) {
    /* NOTE: there are no poorly constructed entries */

    /* different objects possible -> fields[0] */
    Pattern patClient = Pattern.compile("^(CLIENT)");
    Pattern patBook = Pattern.compile("^(BOOK)");
    Pattern patBox = Pattern.compile("^(BOX)");
    Pattern patContainer = Pattern.compile("^(CONTAINER)");
    Pattern patSupplier = Pattern.compile("^(SUPPLIER)");


    /* creating a client */
    // CLIENT|id|name|address
    if (patClient.matcher(fields[0]).matches()) {
      Client c = new Client(fields[1], fields[2], fields[3]);
      _clients.put(c.getClientKey(), c);
      for (Product p: _products.values()) {
        p.setProductNotificationsOn(c);
      }
    }

    /* creating a book */
    // BOOK|id|title|author|isbn|id-supplier|price|stock-critical-value|stock
    if (patBook.matcher(fields[0]).matches()) {
      Supplier s = getSupplier(fields[5]);
      Book b = new Book(fields[1],s,Integer.parseInt(fields[6]),
        Integer.parseInt(fields[7]), fields[2],fields[3],fields[4], 
        Integer.parseInt(fields[8]));

      _products.put(b.getProductKey(),b);
    
      for (String i : _clients.keySet()) {
        b.setProductNotificationsOn(_clients.get(i));
      }
    }

    /* creating a box */
    // BOX|id|service-type|id-supplier|price|stock-critical-value|stock
    if (patBox.matcher(fields[0]).matches()) {
      Supplier s = getSupplier(fields[3]);
      Box b = new Box(fields[1],Integer.parseInt(fields[4]),
        Integer.parseInt(fields[5]),s, fields[2], 
        Integer.parseInt(fields[6]));

      _products.put(b.getProductKey(),b);
      
      for (String i : _clients.keySet()) {
        b.setProductNotificationsOn(_clients.get(i));
      }
    }

    /* creating a container */
    // CONTAINER|id|service-type|service-level|id-supplier|price|stock-critical-value|stock
    if (patContainer.matcher(fields[0]).matches()) {
      Supplier s = getSupplier(fields[4]);
      Container c = new Container(fields[1],Integer.parseInt(fields[5]),
      Integer.parseInt(fields[6]), s,fields[2],fields[3], 
      Integer.parseInt(fields[7]));

      _products.put(c.getProductKey(),c);

      for (String i : _clients.keySet()) {
        c.setProductNotificationsOn(_clients.get(i));
      }
    }

    /* creating a supplier */
    // SUPPLIER|id|name|address
    if (patSupplier.matcher(fields[0]).matches()) {
      Supplier s = new Supplier(fields[1], fields[2], fields[3]);
      _suppliers.put(s.getSupplierKey(), s);
    }
  }
  
  /**
   * Reads a text file (to later process it)
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {

    // for reading the file
    BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    // for each line we're reading
    String line;
    
    while ((line = reader.readLine()) != null) { // read each line

      // split the line when we encounter "|"
      String [] fields = line.split("\\|");

      // register the objects using the fields
      registerFromFields(fields);
    }

    // we're done reading
    reader.close();
  }  
}