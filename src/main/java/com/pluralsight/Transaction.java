package com.pluralsight;
import java.util.ArrayList;
import java.util.Date;


public class Transaction extends Payment{
    private ArrayList<Payment> payments;

    public Transaction(ArrayList<Payment> payments, String description, String vendor, double amount) {
        this.payments = payments;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }


    public void addPayment(Payment payment){
        payments.add(payment);
    }

    private String description;
    private String vendor;
    private double amount;

    public Transaction(String description, String vendor, double amount) {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    //    private String nameOnCard,cardNumber,expirationDate;
//    private int cvv;
//    private Date date;
//    private Time time;
//    private String description,vendor;
//    private double amount;

//    public void addTransaction(String description,double amount){
//        Transaction transactions = new Transaction(new Date(),description,vendor,amount);
//    }

}
