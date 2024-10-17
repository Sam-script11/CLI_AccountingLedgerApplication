package com.pluralsight;
import javax.swing.text.DateFormatter;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static List<Transaction> allTransactions;
    static final String filePath = "src/main/resources/Transactions.csv";
    static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
       // Scanner keyboard = new Scanner(System.in);
//arraylist of all transactions
        allTransactions = loadTransactions();


        System.out.println(allTransactions);

        //loadTransactions() -> this method should only be called once to read from the csv file and populate your arraylist

        boolean homeMenu = true;

        while(homeMenu) {
            displayCommands();
            int command = Integer.parseInt(keyboard.nextLine().trim());
            switch (command) {
                case 1:
                    makeDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    displayLedger();
                    break;
                case 4:
                    System.out.println("Exit the application");
                    homeMenu = false;
                  // System.exit(0);
                    return;
                default:
                    System.out.println("invalid option");
            }

        }

        //the end of our main method
        //add a print statement
       }


    public static void makeDeposit() {

        try {

            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter logger = new BufferedWriter(fw);
            LocalDateTime today = LocalDateTime.now();

            LocalDate date = today.toLocalDate();
            LocalTime time = today.toLocalTime();

            DateTimeFormatter dfmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter tfmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = date.format(dfmt);
            String formattedTime = time.format(tfmt);

                System.out.print("Are you depositing a check or cash? ");
                String checkOrcash = keyboard.nextLine();

                if (checkOrcash.equalsIgnoreCase("cash")) {
                    System.out.print("Please type the amount you are depositing: $");
                    Double amount = Double.parseDouble(keyboard.nextLine());
                  //  keyboard.nextLine(); // consumes new line

                    System.out.print("Enter the description: ");
                    String description = keyboard.nextLine();

                    System.out.print("Enter the vendor:");
                    String vendor = keyboard.nextLine();

                    logger.write(formattedDate + "|" + formattedTime + "|" +description +"|"+vendor+"|"+ amount + "|" ); // add description today the csv string


                    logger.newLine();

                    Transaction newTransaction = new Transaction();
                    newTransaction.setTime(LocalTime.parse(formattedTime));
                    newTransaction.setDate(LocalDate.parse(formattedDate));
                    newTransaction.setDescription(description);
                    newTransaction.setVendor(vendor);
                    newTransaction.setAmount(amount);
                    //newTransaction.setDescription(descr)

                    allTransactions.add(newTransaction);

                } else if (checkOrcash.equalsIgnoreCase("check")) {

                    System.out.print("Please enter the pay to the order of: ");
                    String payTo = keyboard.nextLine();
                    System.out.print("Please enter the amount written on the check:");
                    Double amountOnCheck = Double.parseDouble(keyboard.nextLine());

                 //   System.out.println(" Attempting to deposit... about to call depositFormat() method");

                    String input = depositFormat(payTo,amountOnCheck);

                   // System.out.println(" after the dporit format method - before we call the logger.write()");
                    logger.write(today + input);
                   // System.out.println(" After we called the logger.write method");
                    logger.newLine();

                } else {
                    System.out.println("Invalid entry");
                }
            System.out.println("deposit successfully made");

                logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }



    }

    public static void makePayment() {

        try{
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter logger = new BufferedWriter(fw);
            LocalDateTime today = LocalDateTime.now();

            LocalDate date = today.toLocalDate();
            LocalTime time = today.toLocalTime();

            DateTimeFormatter dfmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter tfmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = date.format(dfmt);
            String formattedTime = time.format(tfmt);

               System.out.print("Enter the description: ");
                String description = keyboard.nextLine();
                System.out.print("Enter the vendor: ");
                String vendor = keyboard.nextLine();
                System.out.print("Enter the amount due: $");
            Double amountDue = Double.parseDouble(keyboard.nextLine());
                //keyboard.nextLine();

            logger.write(formattedDate + "|" + formattedTime + "|" +"|"+description+ "|"+ vendor+"|" +-amountDue); // add description today the csv string
                logger.newLine();


            System.out.println("Payment made successfully");
            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

        public static void displayLedger(){
       // Scanner keyboard = new Scanner(System.in);

            System.out.println("1) All");
            System.out.println("2) Deposits");
            System.out.println("3) Payments");
            System.out.println("4) Reports");
            System.out.println("5) Home");
            System.out.print("Select an option: ");

            boolean ledgerMenu = true;

            while (ledgerMenu) {

                int opt = keyboard.nextInt();
                switch (opt) {
                    case 1:
                        System.out.println(loadTransactions());
                        break;
                    case 2:
                      //  makeDeposit(); viewing all deposits
                        break;
                    case 3:
                       // makePayment(); // viewing all payments
                        break;
                    case 4:
                        // create a method to display reports
                        // this is going to be a smaller subMenu on the ledger menu
                    case 5:
                        System.out.println("Returning to Home Screen");
                        ledgerMenu = false;
                       return;
                    default:
                        System.out.println("invalid entry: ");
                }


            }
        }


                //  method to take input from the user for transaction and formatted
    private static String inputFormat(String description, String vendor, double amount) {
        String dateTime = String.valueOf(LocalDateTime.now());
        return String.join("|", dateTime, description, vendor, String.format("%.2f", amount));
    }

            //created method for deposit format,
    private static String depositFormat(String payTo,double amountOnCheck){

        System.out.println("Inside depositFormat Method");


        return String.join("|",payTo,String.format("%.2f",amountOnCheck));

    }

                //command display, which will be called in the main
    public static void displayCommands(){
        System.out.print("""
                Hello what would you like to do today?
                1.  Make a deposit 
                2.  Make a payment
                3.  View my transaction history
                4.  Exit
                Please make a selection: """);

        }

        //reading from csv file to populate arraylist
    static List<Transaction> loadTransactions(){
        List<Transaction> transactions = new ArrayList<>();


            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                //reading each line from the csv file
                String line;

                while ((line = br.readLine()) != null) {
                    //convert it to a Transaction object
                    String[] values = line.split("\\|");

                    Transaction itemFromFile = new Transaction();

                    itemFromFile.setDate(LocalDate.parse(values[0]));
                    itemFromFile.setTime(LocalTime.parse(values[1]));
                    itemFromFile.setAmount(Double.parseDouble(values[2]));
                    itemFromFile.setDescription(values[3]);
                    itemFromFile.setVendor(values[4]);
                    itemFromFile.setAmount(Double.parseDouble(values[5]));


                    //add it to the transaction arraylist
                    transactions.add(itemFromFile);
                }

        } catch(Exception exp){
            System.out.println(exp.getLocalizedMessage());
        }

        return transactions;
    }
    static void helper(){
      //  Scanner keyboard = new Scanner(System.in);
        //this logic should be in it own method - not inside of this while loop
        try {
            //creating a root the the file named transaction.csv
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter logger = new BufferedWriter(fw);

            //creating a time stamp using a method already in java
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = today.format(fmt);

            //creating and writing  a header to the file
            String heading = ("Date|Time|Description|Vendor|amount");
            logger.write(heading);
            logger.newLine();
            //taking user input to
            System.out.print("What was the item you purchased? ");
            String description = keyboard.nextLine();
            System.out.print("Who was the vendor? ");
            String vendor = keyboard.nextLine();
            System.out.print("How much was the item? $-");
            double amount = keyboard.nextDouble();
            logger.newLine();

            //formatting what is being written to the file
            logger.write(formattedDate+"|"+description+"|"+vendor+"|"+amount);


            logger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }




