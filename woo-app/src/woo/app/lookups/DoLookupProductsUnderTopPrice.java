package woo.app.lookups;

import pt.tecnico.po.ui.Command;                                                                                                              
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;                                                                                                                
import woo.Storefront;                                                                                                                       

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductsUnderTopPrice extends Command<Storefront> {

  private Input<Integer> _priceLimit;

  /**
   * @param storefront
   */
  public DoLookupProductsUnderTopPrice(Storefront storefront) {
    super(Label.PRODUCTS_UNDER_PRICE, storefront);
    _priceLimit = _form.addIntegerInput(Message.requestPriceLimit());
  }

  /**
   * @throws DialogException
   */
  @Override
  public void execute() throws DialogException {
    _form.parse();
    _display.addLine(_receiver.lookupProductsUnderTopPrice(_priceLimit.value()));
    _display.display();
  }
}
