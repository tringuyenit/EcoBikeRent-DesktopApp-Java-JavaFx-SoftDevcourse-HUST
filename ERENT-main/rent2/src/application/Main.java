//package application;
//
//import java.time.LocalDate;
//
//import se.project.interfaces.TransactionUtils;
//import se.project.model.payment.CreditCard;
//import se.project.model.payment.PayByCard;
//import se.project.model.payment.PayService;
//import se.project.model.payment.PayStrategy;
//
//public class Main {
//
//
//  public static void main(String[] args) {
//
//	    int c= 2;
//	     int a = c;
//	    System.out.print(a);
//	     a =3;
//	    System.out.print(c);
//		/*
//		LocalDateTime now = LocalDateTime.now();
//        System.out.println("Before Formatting: " + now);
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatDateTime = now.format(format);
//        System.out.println("After Formatting: " + formatDateTime);
//        */
//        CreditCard card = new CreditCard();
//        PayService payService = new PayService(new PayByCard(card));
//        System.out.println(payService.pay(1000000));
//	    /*
//	       Payment pay = new Payment();
//	       pay.selectPayment(new PayByCard(card));
//
//		   System.out.println(pay.payReturn(40,1200000));*/
//
//  }
//}
