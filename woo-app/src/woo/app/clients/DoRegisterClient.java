package woo.app.clients;

import pt.tecnico.po.ui.Command;   
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;   
import woo.exceptions.DuplicateClientException;
import woo.app.exceptions.DuplicateClientKeyException;                                                                                                          

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront> {

  private Input<String> _clientKey;
  private Input<String> _clientName;
  private Input<String> _clientAddress;

  //FALTAM NOTIFICACOES
  /**
   * @param storefront
   */
  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _clientName = _form.addStringInput(Message.requestClientName());
    _clientAddress = _form.addStringInput(Message.requestClientAddress());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerClient(_clientKey.value(), _clientName.value(), _clientAddress.value());
    } catch (DuplicateClientException e) {
      throw new DuplicateClientKeyException(_clientKey.value());
    }
  }

}
