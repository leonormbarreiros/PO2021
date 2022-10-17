package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        

import woo.exceptions.DuplicateProductException;
import woo.app.exceptions.DuplicateProductKeyException;    
import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.UnknownSTypeException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.exceptions.UnknownLevelException;
import woo.app.exceptions.UnknownServiceLevelException;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {
  
  private Input<String> _productKey;
  private Input<Integer> _productPrice;
  private Input<Integer> _stockCriticalValue;
  private Input<String> _supplierKey;
  private Input<String> _serviceType;
  private Input<String> _serviceLevel;

  /** @param receiver */
  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _productPrice = _form.addIntegerInput(Message.requestPrice());
    _stockCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _serviceType = _form.addStringInput(Message.requestServiceType());
    _serviceLevel = _form.addStringInput(Message.requestServiceLevel());

  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerProductContainer(_productKey.value(), _productPrice.value(), _stockCriticalValue.value(), _supplierKey.value(), _serviceType.value(), _serviceLevel.value());
    } catch (DuplicateProductException e) {
      throw new DuplicateProductKeyException(_productKey.value());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    } catch (UnknownSTypeException e) {
      throw new UnknownServiceTypeException(_serviceType.value());
    } catch (UnknownLevelException e) {
      throw new UnknownServiceLevelException(_serviceLevel.value());
    }
  }
}
