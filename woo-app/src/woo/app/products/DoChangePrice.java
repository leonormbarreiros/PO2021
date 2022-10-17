package woo.app.products;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.UnknownProductException;                                                                                                                        

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  private Input<Integer> _price;
  private Input<String> _productKey;

  /** @param receiver */
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.changePrice(_productKey.value(), _price.value());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(_productKey.value());
    }
  }
}
