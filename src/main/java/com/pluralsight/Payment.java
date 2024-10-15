package com.pluralsight;

public class Payment {
    private String nameOnCard,numbersOnCard,expirationDate;
    private int cvv;
    private double amountDue;

    public Payment(String nameOnCard, String numbersOnCard, String expirationDate, int cvv, double amountDue) {
        this.nameOnCard = nameOnCard;
        this.numbersOnCard = numbersOnCard;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.amountDue = amountDue;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNumbersOnCard() {
        return numbersOnCard;
    }

    public void setNumbersOnCard(String numbersOnCard) {
        this.numbersOnCard = numbersOnCard;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

}
