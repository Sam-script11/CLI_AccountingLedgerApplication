package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);


        while (true) {
            displayCommands();
            int command = Integer.parseInt(keyboard.nextLine());
            switch (command) {
                case 1:
                    displayDeposit(keyboard);
                    break;
                case 2:
                    displayPayment(keyboard);
                    break;
                case 3:
                    displayLedger(keyboard);
                    break;
                case 4:
                    System.out.println("Exit the application");
                    break;
                    default:
                    System.out.println("invalid option");
            }
        }


    }

    public static void displayDeposit(Scanner keyboard) {
        try {

            FileWriter fw = new FileWriter("src/main/resources/Transactions.csv", true);
            BufferedWriter logger = new BufferedWriter(fw);
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.print("Hello, are you making a deposit today? (yes or no) ");
            String optDeposit = keyboard.nextLine();
            if (optDeposit.equalsIgnoreCase("yes")) {
                System.out.print("Are you depositing a check or cash? ");
                String checkOrcash = keyboard.nextLine();
                if (checkOrcash.equalsIgnoreCase("cash")) {
                    System.out.print("Please type the amount you are depositing: $");
                    double amount = keyboard.nextDouble();

                    logger.write(fmt+"You have deposited the amount of $"+amount);
                    logger.newLine();
                    logger.write("You have made a deposit of $"+amount+", have a great day");
                } else if (checkOrcash.equalsIgnoreCase("check")) {
                    System.out.print("Please enter the pay to the order of: ");
                    String payTo = keyboard.nextLine();
                    System.out.print("Please enter your account number: ");
                    String accountNumber = keyboard.nextLine();
                    System.out.print("Please enter the amount written on the check:");
                    Double amountOncheck = keyboard.nextDouble();


                    logger.write(fmt+"you are making a deposit of $"+amountOncheck+ " to the account number ending in: "+accountNumber.substring(accountNumber.length()-4));
                    logger.newLine();

                } else {
                    System.out.println("Invalid entry");
                }

            } else {
                System.out.println("Have a good day");
            }

            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }



    }

    public static void displayPayment(Scanner keyboard) {
        //System.out.println("We appreciate your decision to make a payment today.");

        try{
            FileWriter fw = new FileWriter("src/main/resources/Transactions.csv", true);
            BufferedWriter logger = new BufferedWriter(fw);

            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.print("what is the numbers in front of the card (must be 16 digits): ");
            String numsOnCard = keyboard.nextLine().trim();
            if (numsOnCard.length() !=16 ){
                System.out.println("invalid card number");
            } else {
                System.out.print("What is the name on the back of the card: ");
                String nameOncard = keyboard.nextLine().trim();
                System.out.print("what is the expiration date: ");
                String expirationDate = keyboard.nextLine().trim();
                System.out.print("what is the security code(three digits on the back of the card): ");
                int cvv = keyboard.nextInt();
                System.out.print("How much will you be paying today: $");
                double amountDue = keyboard.nextDouble();

                keyboard.nextLine();

                logger.write(fmt+"You have made a payment of $-"+amountDue+"with the card ending in: "+numsOnCard.substring(numsOnCard.length()-4));
                    logger.newLine();

            }
            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

        public static void displayLedger(Scanner keyboard){

            System.out.println("1) All");
            System.out.println("2) Deposits");
            System.out.println("3) Payments");
            System.out.println("4) Reports");
            System.out.println("5) Home");
            System.out.print("Select an option: ");

            while (true) {
                int opt = keyboard.nextInt();
                switch (opt) {
                    case 1:
                        // create a method to display all entries
                        break;
                    case 2:
                        displayDeposit(keyboard);
                        break;
                    case 3:
                       // displayPayment(keyboard);
                        break;
                    case 4:
                        // create a method to display reports
                    case 5:
                        return;
                    default:
                        System.out.println("invalid entry");
                }


                try {

                    FileWriter fw = new FileWriter("src/main/resources/Transactions.csv", true);
                    BufferedWriter logger = new BufferedWriter(fw);
                    LocalDateTime today = LocalDateTime.now();
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String heading = ("Date|Time|Description|Vendor|amount\n");
                    System.out.print("What was the item you purchased? ");
                    String description = keyboard.next();
                    System.out.print("Who was the vendor? ");
                    String vendor = keyboard.next();
                    System.out.print("How much was the item? $-");
                    double amount = keyboard.nextDouble();
                    System.out.print("");

                    String formattedDate = today.format(fmt);
                    logger.write(formattedDate + "|" + description + "|" + vendor + "|$" + amount);
                    logger.newLine();

                    Transaction transaction1 = new Transaction(description, vendor, amount);
                    logger.write(String.valueOf(transaction1));


                    logger.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    // instantiate the class transaction
        // make sure to get setters and getters for the class Transaction
        //use a method to display the method
        //use arrayList
        // add transaction method key method as string and value amount


    public static void displayCommands(){
        System.out.print("""
                Hello what would you like to do today?
                1.  Make a deposit 
                2.  Make a payment
                3.  View my transaction history
                4.  Exit
                Please make a selection: """);

        }
    }




