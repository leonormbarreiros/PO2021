package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront; 

import woo.exceptions.DuplicateProductException;
import woo.app.exceptions.DuplicateProductKeyException;    
import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;


/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  private Input<String> _productKey;
  private Input<String> _bookTitle;
  private Input<String> _bookAuthor;
  private Input <String> _isbn;
  private Input<Integer> _productPrice;
  private Input<Integer> _stockCriticalValue;
  private Input<String> _supplierKey;

  /** @param receiver */
  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _bookTitle = _form.addStringInput(Message.requestBookTitle());
    _bookAuthor = _form.addStringInput(Message.requestBookAuthor());
    _isbn = _form.addStringInput(Message.requestISBN());
    _productPrice = _form.addIntegerInput(Message.requestPrice());
    _stockCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerProductBook(_productKey.value(), _bookTitle.value(), _bookAuthor.value(), _isbn.value(), _productPrice.value(), _stockCriticalValue.value(), _supplierKey.value());
    } catch (DuplicateProductException e) {
      throw new DuplicateProductKeyException(_productKey.value());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    }
  }
}
