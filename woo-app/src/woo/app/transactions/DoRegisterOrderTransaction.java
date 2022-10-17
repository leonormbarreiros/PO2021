package woo.app.transactions;

import pt.tecnico.po.ui.Command; 
import pt.tecnico.po.ui.DialogException;                                                                                                      
import pt.tecnico.po.ui.Input;     

import java.util.LinkedHashMap;

import woo.Storefront;    

import woo.exceptions.InactiveSupplierException;
import woo.exceptions.UnrelatedSupplierException;
import woo.exceptions.UnknownSupplierException;
import woo.exceptions.UnknownProductException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.WrongSupplierException;                                                                                    

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  private Input<String> _supplierKey;
  private Input<String> _productKey;
  private Input<Integer> _amount;
  private Input<String> _more;
  //maneira de ir guardando os produtos de uma mesma transação
  private LinkedHashMap<String,Integer> products = new LinkedHashMap<String,Integer>();
  private int newAmount;

  /** @param receiver */
  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
  }

  /**
   * @throws DialogException
   */
  @Override
  public final void execute() throws DialogException {
    
    
    try {
      _form.clear();
      products.clear();
      _supplierKey = _form.addStringInput(Message.requestSupplierKey());
      _productKey = _form.addStringInput(Message.requestProductKey());
      _amount = _form.addIntegerInput(Message.requestAmount());
      _more = _form.addStringInput(Message.requestMore());
      _form.parse();

      products.put(_productKey.value(),_amount.value());
      
      while (_more.value().equals("s")) {
        _form.clear();
        _productKey = _form.addStringInput(Message.requestProductKey());
        _amount = _form.addIntegerInput(Message.requestAmount());
        _more = _form.addStringInput(Message.requestMore());
        _form.parse();
        newAmount = _amount.value();
        if (products.containsKey(_productKey.value())) {
          newAmount = _amount.value() + products.get(_productKey.value());
        }
        products.put(_productKey.value(),newAmount);
      }
    //quando parar de acrescentar a mesma encomenda
     _receiver.registerOrderTransaction(_supplierKey.value(),products);
    } catch (InactiveSupplierException e) {
      throw new UnauthorizedSupplierException(_supplierKey.value());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(_productKey.value());
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierKey.value());
    } catch (UnrelatedSupplierException e) {
      throw new WrongSupplierException(_supplierKey.value(),_productKey.value());
    }
  }

}
