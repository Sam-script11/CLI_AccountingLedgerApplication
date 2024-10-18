package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    //created an Array list of Transaction and named the inventory all transaction, easter to be used throughout the program
    static List<Transaction> allTransactions;
    //created a filepath for the file name Transations.csv easier to use and demonstrating DRY(Don't repeat Yourself)
    static final String filePath = "src/main/resources/Transactions.csv";
    //finalized a Scanner to use inside the class but outside the main method,
    // which will be used throughout the code without creating new scanner (DRY)
    static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        allTransactions = loadTransactions();

        //displaying all the deposits and transactions
        System.out.println(allTransactions);


        boolean homeMenu = true;
            //inside a loop to keep going if they want to access other cases
        while(homeMenu) {
            // created, called  and displaying commands
            displayCommands();
            int command = Integer.parseInt(keyboard.nextLine().trim());
            switch (command) {
                //creating different cases for the different commands
                // if user input is invalid, will print default
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
                    return;
                default:
                    System.out.println("invalid option");
            }

        }

       }


    public static void makeDeposit() {

        try {
                //writing to the file
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter logger = new BufferedWriter(fw);
            LocalDateTime today = LocalDateTime.now();

            //broken down date and time
            LocalDate date = today.toLocalDate();
            LocalTime time = today.toLocalTime();

            //formatted time and year
            DateTimeFormatter dfmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter tfmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = date.format(dfmt);
            String formattedTime = time.format(tfmt);

                    //giving the user two option and with one, it will implement the logic and format
                System.out.print("Are you depositing a check or cash? ");
                String checkOrcash = keyboard.nextLine();

                if (checkOrcash.equalsIgnoreCase("cash")) {
                    System.out.print("Please type the amount you are depositing: $");
                    //inside the parse, we are consuming a newline since double does not
                    Double amount = Double.parseDouble(keyboard.nextLine());

                    //user input
                    System.out.print("Enter the description: ");
                    String description = keyboard.nextLine();

                    System.out.print("Enter the vendor:");
                    String vendor = keyboard.nextLine();
                        //formatting our user inputs, amount will be positive since we are making deposits
                    logger.write(formattedDate + "|" + formattedTime + "|" +description +"|"+vendor+"|"+ amount + "|$" );


                    logger.newLine();

                    //creating a new object after instantiating Transaction class
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTime(LocalTime.parse(formattedTime));
                    newTransaction.setDate(LocalDate.parse(formattedDate));
                    newTransaction.setDescription(description);
                    newTransaction.setVendor(vendor);
                    newTransaction.setAmount(amount);

                    //adding the new object to the arrayList initialized in the beggiing called allTransactions
                    allTransactions.add(newTransaction);


                        //if user chooses check, they will be prompted to enter the info and formatted
                } else if (checkOrcash.equalsIgnoreCase("check")) {

                    System.out.print("Please enter the pay to the order of: ");
                    String payTo = keyboard.nextLine();
                    System.out.print("Please enter the amount written on the check: $");
                    Double amountOnCheck = Double.parseDouble(keyboard.nextLine());

                        //created a method to format the check
                    String input = depositFormat(payTo,amountOnCheck);
                        //broken down date and time, the input is concatained with the |
                    logger.write(formattedDate+"|" +formattedTime+"|" +input);

                    //creating new line
                    logger.newLine();

                } else {
                    System.out.println("Invalid entry");
                }
                    //user confirmation when logic is completed
            System.out.println("deposit successfully made");

                logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }



    }

    public static void makePayment() {

        try{
                    //writing to the file path and append
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter logger = new BufferedWriter(fw);
            LocalDateTime today = LocalDateTime.now();

            //formatted time and date for easier format
            LocalDate date = today.toLocalDate();
            LocalTime time = today.toLocalTime();

            //separated Date and Time in order to use when formating with |
            DateTimeFormatter dfmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter tfmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = date.format(dfmt);
            String formattedTime = time.format(tfmt);

                    //asking the user to enter description, vendor, and amount due
               System.out.print("Enter the description: ");
                String description = keyboard.nextLine();
                System.out.print("Enter the vendor: ");
                String vendor = keyboard.nextLine();
                System.out.print("Enter the amount due: $");
            Double amountDue = Double.parseDouble(keyboard.nextLine());


                //wring to the file with the formatted date,time, description,vendor and amount due with negative since we are making payment
            logger.write(formattedDate + "|" + formattedTime + "|" +"|"+description+ "|"+ vendor+"|$" +-amountDue);
                //crating noew line
                logger.newLine();

                //print to let the user know the logic was successful
            System.out.println("Payment made successfully");
            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
               // method for ledger and its menu
        public static void displayLedger() {
        boolean ledgerMenu = true;
            while(ledgerMenu) {
                //created a menu for ledger
                System.out.println("1) All");
            System.out.println("2) Deposits");
            System.out.println("3) Payments");
            System.out.println("4) Reports");
            System.out.println("5) Home");
            System.out.print("Select an option: ");

                // initialized option for the cases and removed any unwanted space using trim
                int opt = Integer.parseInt(keyboard.nextLine().trim());
                // from the menu, you are given different cases, used switch for user desires
                // will print the default if invalid entry
                switch (opt) {
                    case 1:
                        allEntries();
                        break;
                    case 2:
                        deposits();
                        break;
                    case 3:
                        payments();
                        break;
                    case 4:
                        displayReports();
                    case 5:
                        System.out.println("Returning to Home Screen");
                        ledgerMenu = false;
                        break;
                    default:
                        System.out.println("invalid entry: ");
                }
            }

        }
            //created to be called for the switch cases and to be called for the ledger menu
    public static void allEntries() {
        // Loop through the already loaded 'allTransactions' list instead of reloading it
        for (Transaction transaction : allTransactions) {
            // if the transaction is not empty from teh all transactions it will print the transaction
            if (transaction != null) {
                System.out.println(transaction);
            }
        }
    }


    //created to be called for the switch cases and to be called for the ledger menu
    public static void deposits() {

        // iterating through all the arrayList of transaction(for each), if transaction is not empty,
        // and amount is greater than 0, since deposits are positive
        // it will print the transaction
        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }

    //created to be called for the switch cases and to be called for the ledger menu
    public static void payments() {
        // iterating through all the arrayList of transaction, if transaction is not empty, and amount is less than 0, since payments are negative
        // it will print the transaction

        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }


// Created method to demonstrate the requirement for generating reports
    public static void displayReports() {
        //inialized the report to a boolean therefore can be used to loop through the code block
        boolean report = true;
        while (report) {
            System.out.println("""
                Reports
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                0) Back
                Select an option:
                """);
            int option = keyboard.nextInt();
            keyboard.nextLine();
            // switch case for the different cases which user can choose from
            switch (option){
                // created an outline for case for reports
                case 1:
                    //dispayMonthToDate();
                    break;
                case 2:
                    //dispayPreviousMonth();
                    break;
                case 3:
                    //displayYearToDate();
                    break;
                case 4:
                    //displayPreviousYear();
                case 5:
                    //
                    //searchByVendor();
                case 0:
                    report = false;
                    break;
                default:
                    // if user enters an invalid entry, it will display the default
                    System.out.println("invalid entry");
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
                    //Date is at the index of 0
                    itemFromFile.setDate(LocalDate.parse(values[0]));
                    //Time is at the index of 1
                    itemFromFile.setTime(LocalTime.parse(values[1]));
                    // Description is at index 2
                    itemFromFile.setDescription(values[2]);
                    // Vendor is at index 3
                    itemFromFile.setVendor(values[3]);
                    // Amount is at index 4
                    itemFromFile.setAmount(Double.parseDouble(values[4]));


                    //add it to the transaction arraylist
                    transactions.add(itemFromFile);
                    System.out.println("loaded transaction "+itemFromFile);

                }

        } catch(Exception exp){
            System.out.println(exp.getLocalizedMessage());
        }

        return transactions;
    }


    }


