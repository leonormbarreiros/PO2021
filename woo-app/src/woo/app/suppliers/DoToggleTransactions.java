package woo.app.suppliers;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        
import woo.exceptions.UnknownSupplierException;
import woo.app.exceptions.UnknownSupplierKeyException;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  private Input<String> _supplierKey;

  /** @param receiver */
  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    try { boolean v = _receiver.toggleTransactions(_supplierKey.value());
      if (v == true) {
        _display.addLine(Message.transactionsOn(_supplierKey.value()));
        _display.display();
      } else {
        _display.addLine(Message.transactionsOff(_supplierKey.value()));
        _display.display();
      }
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    }
  }
}
