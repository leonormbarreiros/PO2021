package woo.app.suppliers;

import pt.tecnico.po.ui.Command;     

import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;                                                                                                          
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  private Input<String> _supplierKey;

  /** @param receiver */
  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.showSupplierTransactions(_supplierKey.value()));
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    }
    _display.display();
  }

}
