package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        //deposit(keyboard);
        //payment(keyboard);
        displayLedger(keyboard);

    }

    public static void deposit(Scanner keyboard) {
        System.out.print("Hello, are you making a deposit today? (yes or no)");
        String optDeposit = keyboard.nextLine();
        if (optDeposit.equalsIgnoreCase("yes")) {
            System.out.print("are you depositing a check or cash");
            String checkOrcash = keyboard.nextLine();
            if (checkOrcash.equalsIgnoreCase("cash")) {
                System.out.println("please enter the amount you are depositing: ");
                double amount = keyboard.nextDouble();

                System.out.printf("you have deposited the amount of $%s", amount);
            } else if (checkOrcash.equalsIgnoreCase("check")) {
                System.out.print("please enter the pay to the order of: ");
                String payTo = keyboard.nextLine();
                System.out.print("Please enter your account number: ");
                String accountNumber = keyboard.nextLine();
                System.out.print("Please enter the amount written on the check:");
                Double amountOncheck = keyboard.nextDouble();

            } else {
                System.out.println("Invalid entry");
            }

        }else{
            System.out.println("Have a good day");
        }
    }

    public static void payment(Scanner keyboard) {
        //System.out.println("We appreciate your decision to make a payment today.");


        System.out.print("what is the numbers in front of the card (must be 16 digits): ");
        String numbersOncard = keyboard.nextLine().trim();
        System.out.print("What is the name on the back of the card: ");
        String nameOncard = keyboard.nextLine().trim();
        System.out.print("what is the expiration date: ");
        String expirationDate = keyboard.nextLine().trim();
        System.out.print("what is the security code(three digits on the back of the card): ");
        int cvv = keyboard.nextInt();
        System.out.print("How much will you be paying today: $");
        double amountDue = keyboard.nextDouble();

        System.out.printf("you have made the payment of %.2f$-",amountDue);

    }

        public static void displayLedger(Scanner keyboard){
            try{
                FileWriter fw = new FileWriter("src/main/resources/Tranctions.csv",true);
                BufferedWriter logger = new BufferedWriter(fw);
                LocalDateTime today = LocalDateTime.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                System.out.println("what was the item you purchased: ");
                String description = keyboard.nextLine();
                System.out.println("who was the vendor: ");
                String vendor = keyboard.nextLine();
                System.out.println("how much was the item: ");
                double amount = keyboard.nextDouble();

                String formattedDate = today.format(fmt);
                logger.write("Date|Time|Description|Vendor|amount");
                logger.newLine();
                logger.write(formattedDate+ "|"+description+ "|"+vendor+ "|"+amount);
                logger.newLine();



                logger.close();
            }catch (IOException e){
                e.printStackTrace();
            }


        }
        // instantiate the class transaction
        // make sure to get setters and getters for the class Transaction
        //use a method to display the method
        //use arrayList
        // add transaction method key method as string and value amount

    }



