package se.project.dao.api.transaction;

public interface IValidTransact {
	public boolean checkTransactFinish(int custId);                // check finish pay
	public boolean checkSameCard(String cardNum, String orderId);  // card same with previous pay
	public boolean checkCardUsed(String cardNum, String orderId);  // card used or not
}
