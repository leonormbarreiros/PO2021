package woo.app.suppliers;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;           
import woo.exceptions.DuplicateSupplierException;
import woo.app.exceptions.DuplicateSupplierKeyException;                                                                                                            

/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {
  
  private Input<String> _supplierKey;
  private Input<String> _supplierAddress;
  private Input<String> _supplierName;


  /** @param receiver */
  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _supplierName = _form.addStringInput(Message.requestSupplierName());
    _supplierAddress = _form.addStringInput(Message.requestSupplierAddress());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
    _receiver.registerSupplier(_supplierKey.value(), _supplierName.value(), _supplierAddress.value());
    } catch (DuplicateSupplierException e) {
      throw new DuplicateSupplierKeyException(_supplierKey.value());
    }
  }
}

