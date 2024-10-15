package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Scanner;

public class Transaction {
    private Date date;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(Date date, String description, String vendor, double amount) {
        this.date = date;
        this.description = description;
        this.vendor = vendor;
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
