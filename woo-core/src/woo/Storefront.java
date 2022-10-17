package woo;

import woo.exceptions.*;
import java.io.*;

import java.util.LinkedHashMap;

/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();

  /** Will keep track of file state. */
  private boolean save = false;

  /*-----------------------------------------MAIN------------------------------*/

  public int getAvailableBalance() {
    return _store.getAvailableBalance();
  }

  public int getAccountingBalance() {
    return _store.getAccountingBalance();
  }

  /*-----------------------------------------DATE------------------------------*/

   /**
   * Returns the current system date.
   */
  public int getCurrentDate() {
    return _store.getCurrentDate();
  }
  
  /**
   * Increases the current system date.
   * @param days
   * @throws InvalidDaysException
   */
  public void advanceCurrentDate(int days) throws InvalidDaysException {
    save = true;
    _store.advanceCurrentDate(days);
  }

  /*---------------------------------------------CLIENTS-----------------------*/

  /**
   * Registers a new client.
   * @param clientKey
   * @param clientName
   * @param clientAddress
   * @throws DuplicateClientException
   */
  public void registerClient(String clientKey, String clientName, 
    String clientAddress) throws DuplicateClientException {
    save = true;
    _store.registerClient(clientKey, clientName, clientAddress);
  }
  
  /**
   * Searches for a specific client.
   * @param clientKey
   * @throws UnknownClientException
   */
  public String showClient(String clientKey) throws UnknownClientException {
    return _store.showClient(clientKey);
  }

  /**
   * Returns (a String containing) all clients.
   */
  public String showAllClients() {
    return _store.showAllClients();
  }

  public boolean toggleProductNotifications(String clientKey,String productKey) throws UnknownClientException, UnknownProductException {
    save = true;
    return _store.toggleProductNotifications(clientKey, productKey);
  }

  public String showClientTransactions(String clientKey) throws UnknownClientException {
    return _store.showClientTransactions(clientKey);
  }

  /*----------------------------------------------PRODUCTS---------------------*/

  /**
   * Returns (a String containing) all products.
   */
  public String showAllProducts() {
    return _store.showAllProducts();
  }

  /**
   * 
   * @param productKey
   * @param price
   * @throws UnknownProductException
   */
  public void changePrice(String productKey, int price) throws UnknownProductException {
    
    save = true;
    _store.changePrice(productKey, price);
  }

  /**
   * 
   * @param productKey
   * @param productPrice
   * @param stockCriticalValue
   * @param supplierKey
   * @param serviceType
   * @throws DuplicateProductException
   * @throws UnknownSupplierException
   * @throws UnknownSTypeException
   */
  public void registerProductBox(String productKey,int productPrice, int stockCriticalValue,
  String supplierKey, String serviceType) 
  throws DuplicateProductException, UnknownSupplierException, UnknownSTypeException {

    save = true;
    _store.registerProductBox(productKey, productPrice, stockCriticalValue, supplierKey, serviceType);
  }

  public void registerProductContainer(String productKey,int productPrice, int stockCriticalValue,
  String supplierKey, String serviceType, String serviceLevel) 
  throws DuplicateProductException, UnknownSupplierException, UnknownSTypeException, UnknownLevelException {

    save = true;
    _store.registerProductContainer(productKey, productPrice, stockCriticalValue, supplierKey, serviceType, serviceLevel);
  }

  /**
   * 
   * @param productKey
   * @param bookTitle
   * @param bookAuthor
   * @param isbn
   * @param productPrice
   * @param stockCriticalValue
   * @param supplierKey
   * @throws DuplicateProductException
   * @throws UnknownSupplierException
   */
  public void registerProductBook(String productKey, String bookTitle, String bookAuthor, 
  String isbn, int productPrice, int stockCriticalValue, String supplierKey) 
  throws DuplicateProductException, UnknownSupplierException {
    
    save = true;
    _store.registerProductBook(productKey, bookTitle, bookAuthor, isbn, productPrice, stockCriticalValue, supplierKey);
  }

  /*----------------------------------------------SUPPLIERS--------------------*/

   /**void
   * Registers a new supplier.
   * @param supplierKey
   * @param supplierName
   * @param supplierAddress
   * @throws DuplicateSupplierException
   */
  public void registerSupplier(String supplierKey,String supplierName,
    String supplierAddress) throws DuplicateSupplierException {
    save = true;
    _store.registerSupplier(supplierKey, supplierName, supplierAddress);
  }

  /**
   * Returns (a String containing) all suppliers.
   */
  public String showAllSuppliers() {
    return _store.showAllSuppliers();
  }

  /**
   * Registers a new supplier.
   * @param supplierKey
   */
  public boolean toggleTransactions(String supplierKey) throws UnknownSupplierException {
    save = true;
    return _store.toggleTransactions(supplierKey);
  }

  public String showSupplierTransactions(String supplierKey) throws UnknownSupplierException {
    return _store.showSupplierTransactions(supplierKey);
  }

  /*-------------------------------------------TRANSACTIONS--------------------*/

  public String showTransaction(int transactionKey) throws UnknownTransactionException{
    return _store.showTransaction(transactionKey);
  }

  /**
   * Registers a new order transaction.
   * @param supplierKey
   * @param productKey
   * @param amount
   */
  public void registerOrderTransaction(String supplierKey, LinkedHashMap<String,Integer> adding) throws UnrelatedSupplierException, 
  InactiveSupplierException, UnknownSupplierException, UnknownProductException {
    save = true;
    _store.registerOrderTransaction(supplierKey,adding);
  }

  public void registerSaleTransaction(String clientKey,int paymentDeadline,String productKey,int amount)
  throws OutOfStockException, UnknownProductException, UnknownClientException {
    save = true;
    _store.registerSaleTransaction(clientKey,paymentDeadline,productKey,amount);
  }

  public void pay(int transactionKey) throws UnknownTransactionException {
    save = true;
    _store.pay(transactionKey);
  }

  /*-------------------------------------------LOOKUPS-------------------------*/

  /**
   * 
   * @param priceLimit
   * @return
   */
  public String lookupProductsUnderTopPrice(int priceLimit) {
    return _store.lookupProductsUnderTopPrice(priceLimit);
  }

  public String lookupPaymentsByClient(String clientKey) throws UnknownClientException {
    return _store.lookupPaymentsByClient(clientKey);
  }

  /*--------------------------------------------FILE MANIPULATION--------------*/


  /**
   * 
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() 
    throws IOException, FileNotFoundException, MissingFileAssociationException {
    
      ObjectOutputStream output = 
        new ObjectOutputStream(new BufferedOutputStream
          (new FileOutputStream(_filename)));

    output.writeObject(_store);
    output.close();
    save = false;
  }

  /**
   * 
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, 
    FileNotFoundException, IOException {

    _filename = filename;
    save();
  }

  /** 
   * Returns the file state.
   */ 
  public boolean getSave() {
    return save;
  }

  /** 
   * Returns the file name.
   */ 
  public String getFileName() {
    return _filename;
  }
 
  /** 
   * Sets the file name.
   * @param filename
   */  
  public void setFileName(String filename) {
    save = true;
    _filename = filename;
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException, IOException, FileNotFoundException {

      // for reading the file 
      ObjectInputStream input = 
      new ObjectInputStream(new BufferedInputStream
        (new FileInputStream(filename)));

      // the file contains an object: the store to be loaded
      _store = (Store) input.readObject();

      // we're done reading the file
      input.close();
      
      _filename = filename;
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile);
    }
    save = true;
  }

}