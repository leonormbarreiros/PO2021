package woo.app.transactions;

import pt.tecnico.po.ui.Command;    

import woo.exceptions.UnknownProductException;
import woo.app.exceptions.UnknownProductKeyException;   

import woo.exceptions.OutOfStockException;
import woo.app.exceptions.UnavailableProductException;

import woo.exceptions.UnknownClientException;
import woo.app.exceptions.UnknownClientKeyException;  
                                                                                                       
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                               

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {
  
  private Input<String> _clientKey;
  private Input<Integer> _paymentDeadline;
  private Input<String> _productKey;
  private Input<Integer> _amount;

  /** @param receiver */
  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _paymentDeadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    _productKey = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerSaleTransaction(_clientKey.value(),_paymentDeadline.value(),
      _productKey.value(),_amount.value());
    } catch (OutOfStockException e) {
      throw new UnavailableProductException(_productKey.value(),_amount.value(), e.getAvailableAmount());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(_productKey.value());
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(_clientKey.value());
    } 
  }

}
