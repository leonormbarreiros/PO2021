package woo.app.products;

import pt.tecnico.po.ui.Command;  
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;  
import pt.tecnico.po.ui.DialogException;      

import woo.exceptions.DuplicateProductException;
import woo.app.exceptions.DuplicateProductKeyException;    
import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.UnknownSTypeException;
import woo.app.exceptions.UnknownServiceTypeException;
                                                                                                                      

/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<Storefront> {

  private Input<String> _productKey;
  private Input<Integer> _productPrice;
  private Input<Integer> _stockCriticalValue;
  private Input<String> _supplierKey;
  private Input<String> _serviceType;

  /** @param receiver */
  public DoRegisterProductBox(Storefront receiver) {
    super(Label.REGISTER_BOX, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _productPrice = _form.addIntegerInput(Message.requestPrice());
    _stockCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _serviceType = _form.addStringInput(Message.requestServiceType());
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerProductBox(_productKey.value(), _productPrice.value(), _stockCriticalValue.value(), _supplierKey.value(), _serviceType.value());
    } catch (DuplicateProductException e) {
      throw new DuplicateProductKeyException(_productKey.value());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    } catch (UnknownSTypeException e) {
      throw new UnknownServiceTypeException(_serviceType.value());
    }
  }
}
