package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                             
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                        

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<Storefront> {

  /**
   * @param storefront
   */
  public DoShowAllClients(Storefront storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    _display.addLine(_receiver.showAllClients());
    _display.display();
  }
}
