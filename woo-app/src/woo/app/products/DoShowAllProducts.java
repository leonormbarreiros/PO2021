package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;                                                                                                                        

/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<Storefront> {

  /** @param receiver */
  public DoShowAllProducts(Storefront receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    _display.addLine(_receiver.showAllProducts());
    _display.display();
  }

}
