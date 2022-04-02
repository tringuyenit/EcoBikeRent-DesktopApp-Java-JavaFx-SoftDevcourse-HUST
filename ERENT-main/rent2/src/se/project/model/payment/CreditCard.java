package se.project.model.payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CreditCard {

	private String bank;

	private String cardNum;

	private LocalDate date;

	private String name;

	private double money = 1000000000;

	public CreditCard() {

	}

	public CreditCard(String bank, String cardNum, LocalDate date, String name) {
		this.bank = bank;
		this.cardNum = cardNum;
		this.date = date;
		this.name = name;
	}

	public CreditCard(String bank, LocalDate date, String name) {
		// TODO Auto-generated constructor stub
		this.bank = bank;
		this.date = date;
		this.name = name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	/*
	 * @date : date user enter
	 * 
	 * return check valid date
	 */

	// check expire
	public boolean validateDate(LocalDate date) {

		LocalDate today = LocalDate.now();
		// check right format

		// check before today expired card
		if (date.isBefore(today)) {
			System.out.println(today.toString()+" "+ date.toString());
			return true;
		}

		return false;
	}

	/*
	 * @format : date format (exp: yyyy-MM-dd)
	 * 
	 * @value : date string return check right format
	 */
	public boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	public void credit(int amount) {
		setMoney(getMoney() + amount);
	}

	public boolean debit(double money2) {
		if (money2 > money) {
			return false;
		} else {
			money -= money2;
			return true;
		}
	}
}
