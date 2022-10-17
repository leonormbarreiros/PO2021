package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;
import woo.Storefront;        
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.UnknownClientException;                                                                                                               

/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {

  private Input<String> _clientKey;

  /**
   * @param storefront;
   */
  public DoShowClient(Storefront storefront) {
    super(Label.SHOW_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.addLine(_receiver.showClient(_clientKey.value()));
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(_clientKey.value());
    }
    _display.display();
  }
}
