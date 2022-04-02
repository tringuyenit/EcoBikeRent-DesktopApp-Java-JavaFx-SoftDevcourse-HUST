package se.project.model.payment;

public class PayByCard implements PayStrategy{

  // update balance
  private CreditCard card;

  public PayByCard(CreditCard card) {
    this.card = card;
  }

  public boolean pay(double paymentAmount) {
    // TODO Auto-generated method stub
    return card.debit(paymentAmount);
  }

   // true thanh cong
  public boolean pay(int deposit, double total) {
    // TODO Auto-generated method stub
    if(card.debit((int)total)) {
    card.credit(deposit);
    return true;
   }
     return false;
}
  }
