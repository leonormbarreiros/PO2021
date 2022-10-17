package woo.app.transactions;

import pt.tecnico.po.ui.Command;    
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;    
                                                                                                            
import woo.Storefront;                                                                                                                        
import woo.exceptions.UnknownTransactionException;
import woo.app.exceptions.UnknownTransactionKeyException;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  private Input<Integer> _transactionKey;
  
  /** @param storefront */
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    _transactionKey = _form.addIntegerInput(Message.requestTransactionKey());
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.pay(_transactionKey.value());
    } catch (UnknownTransactionException e) {
      throw new UnknownTransactionKeyException(_transactionKey.value());
    }
  }

}
