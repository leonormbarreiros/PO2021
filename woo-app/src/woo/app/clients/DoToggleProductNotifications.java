package woo.app.clients;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;          
import woo.exceptions.UnknownClientException;
import woo.app.exceptions.UnknownClientKeyException;     
import woo.exceptions.UnknownProductException;
import woo.app.exceptions.UnknownProductKeyException;                                                                                                         

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  private Input<String> _clientKey;
  private Input<String> _productKey;

  /**
   * @param storefront
   */
  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _productKey = _form.addStringInput(Message.requestProductKey());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    try { boolean v = _receiver.toggleProductNotifications(_clientKey.value(),_productKey.value());
      if (v == true) {
        _display.addLine(Message.notificationsOn(_clientKey.value(),_productKey.value()));
        _display.display();
      } else {
        _display.addLine(Message.notificationsOff(_clientKey.value(),_productKey.value()));
        _display.display();
      }
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(_clientKey.value());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(_productKey.value());
    }
  }

}
