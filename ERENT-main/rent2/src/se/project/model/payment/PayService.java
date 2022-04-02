package se.project.model.payment;

public class PayService {
  private PayStrategy payMethod;
  public PayService(PayStrategy method) {
	  this.payMethod = method;
  }
  
  public boolean pay(double paymentAmount) {
	  return payMethod.pay(paymentAmount);
  }
  public boolean pay(int deposit, double total) {
	  return payMethod.pay(deposit, total);
  }
}
