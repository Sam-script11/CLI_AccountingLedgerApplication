package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class Transaction{



    private String description;
    private String vendor;
    private double amount;

    private LocalDate date;
    private LocalTime time;


    public Transaction(String description, String vendor, double amount) {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    Transaction(){

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    // descr + "|" + vendor + "|"
}
//