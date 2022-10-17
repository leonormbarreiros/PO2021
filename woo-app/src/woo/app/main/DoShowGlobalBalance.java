package woo.app.main;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                       

/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<Storefront> {

  /** @param receiver */
  public DoShowGlobalBalance(Storefront receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  @Override
  public final void execute() {
    int available = _receiver.getAvailableBalance();
    int accounting = _receiver.getAccountingBalance();

    _display.addLine(Message.currentBalance(available, accounting));
    _display.display();
  }
}
