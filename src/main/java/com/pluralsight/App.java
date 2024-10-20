package com.pluralsight;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {
    //created an Array list of Transaction and named the inventory all transaction, easier to be used throughout the program

    static List<Transaction> allTransactions;
    //created a filepath for the file name Transactions.csv easier to use and demonstrating DRY(Don't repeat Yourself)
    static final String filePath = "src/main/resources/Transactions.csv";
    static final Scanner keyboard = new Scanner(System.in);
    //finalized a Scanner to use inside the class but outside the main method,
    // which will be used throughout the code without creating new scanner (DRY)

    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("src/main/resources/download.jpg");
        String [] homeButton = {"continue"};

        String title = "Welcomes to SamScript Capital \n Building Wealth, One Script at a Time";
        JOptionPane.showOptionDialog(null,title,"Title",
                JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,image,homeButton,1); // formatting the home screen

        String str = JOptionPane.showInputDialog("What is your name "); // used to create dialog box with the message on top of it
        JOptionPane.showOptionDialog(null,"Hello "+str,"Greetings",JOptionPane.YES_NO_OPTION, // after user enters into the text box,
                // it is assigned to str
                JOptionPane.INFORMATION_MESSAGE,null,homeButton,1);
        //displaying all the deposits and transactions
       allTransactions = loadTransactions();
        //inside a loop to keep going if they want to access other cases
       // System.out.println(allTransactions);
        boolean homeMenu = true;
        while(homeMenu) {

            //creating different cases for the different commands
            // if user input is invalid, will print default
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
                    break;
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

            //giving the user two options and with one, it will implement the logic and format

            System.out.print("Are you depositing a check or cash? ");
                String checkOrcash = keyboard.nextLine();

                if (checkOrcash.equalsIgnoreCase("cash")) {
                    System.out.print("Please type the amount you are depositing: $");
                    Double amount = Double.parseDouble(keyboard.nextLine());

                    //user input

                    System.out.print("Enter the description: ");
                    String description = keyboard.nextLine();

                    System.out.print("Enter the vendor:");
                    String vendor = keyboard.nextLine();
                    //formatting our user inputs, amount will be positive since we are making deposits

                    logger.write(formattedDate + "|" + formattedTime + "|" +description +"|"+vendor+"|$"+ amount ); // add description today the csv string


                    logger.newLine();
                    //creating a new object after instantiating Transaction class

                    Transaction newTransaction = new Transaction();
                    newTransaction.setTime(LocalTime.parse(formattedTime));
                    newTransaction.setDate(LocalDate.parse(formattedDate));
                    newTransaction.setDescription(description);
                    newTransaction.setVendor(vendor);
                    newTransaction.setAmount(amount);

                    //adding the new object to the arrayList initialized in the beginning called allTransactions

                    allTransactions.add(newTransaction);

                } else if (checkOrcash.equalsIgnoreCase("check")) {

                    System.out.print("Please enter the pay to the order of: ");
                    String payTo = keyboard.nextLine();
                    System.out.print("Please enter the amount written on the check: $");
                    Double amountOnCheck = Double.parseDouble(keyboard.nextLine());

                    //created a method to format the check

                    String input = depositFormat(payTo,amountOnCheck);

                    logger.write(formattedDate+"|" +formattedTime+"|" +input);

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
            //writing to the file path and append
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter logger = new BufferedWriter(fw);
            LocalDateTime today = LocalDateTime.now();

            LocalDate date = today.toLocalDate();
            LocalTime time = today.toLocalTime();

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

            //writing to the file with the formatted date, time, description, vendor, and amount due as negative since we are making payment
            logger.write(formattedDate + "|" + formattedTime + "|" +"|"+description+ "|"+ vendor+"|$" +-amountDue); // add description today the csv string
                logger.newLine();

            //print to let the user know the logic was successful
            System.out.println("Payment made successfully");
            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
        public static void displayLedger() {
            // method for ledger and its menu

            System.out.println("1) All");
            System.out.println("2) Deposits");
            System.out.println("3) Payments");
            System.out.println("4) Reports");
            System.out.println("5) Home");
            System.out.print("Select an option: ");
            boolean ledgerMenu = true;
            do{

                // initialized option for the cases and removed any unwanted space using trim
                int opt = Integer.parseInt(keyboard.nextLine().trim());

                //created to be called for the switch cases and to be called for the ledger menu
                switch (opt) {
                    // from the menu, you are given different cases, used switch for user desires
                    // will print the default if invalid entry
                    case 1:
                        allEntries();
                        allTransactions = loadTransactions();
                        break;
                    case 2:
                        deposits();
                        break;
                    case 3:
                        payments();
                        break;
                    case 4:
                        displayReports();
                        break;
                    case 5:
                        System.out.println("Returning to Home Screen");
                        ledgerMenu = false;
                        break;
                    default:
                        System.out.println("invalid entry: ");
                }
            }while (ledgerMenu);
        }

    public static void allEntries() {
        System.out.println("Date|Time|Description|Vendor|Amount");

        // Loop through the already loaded 'allTransactions' list instead of reloading it
        for (Transaction transaction : allTransactions) {
            if (transaction != null) {
                // Print the transaction details
                System.out.printf("%s|%s|%s|%s|%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    //created to be called for the switch cases and to be called for the ledger menu
    public static void deposits() {
        // iterating through all the arrayList of transactions (for each), if transaction is not empty,
        // and amount is greater than 0, since deposits are positive
        // it will print the transaction
        System.out.println("Date|Time|Description|vendor|Amount");
        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getAmount() > 0) {
                System.out.printf("%s|%s|%s|%s|%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }
    //created to be called for the switch cases and to be called for the ledger menu

    public static void payments() {
        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getAmount() < 0) {
                System.out.printf("%s|%s|%s|%s|%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    // Created method to demonstrate the requirement for generating reports
    public static void displayReports() {
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

                try {
                    int option = Integer.parseInt(keyboard.nextLine().trim());
                    switch (option) {
                        case 1:
                            monthToDateReport();
                            break;
                        case 2:
                            previousMonthReport();
                            break;
                        case 3:
                            yearToDateReport();
                            break;
                        case 4:
                            previousYearReport();
                            break;
                        case 5:
                            searchByVendor();
                            break;
                        case 0:
                            report = false; // Exit the report menu
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                    // Handle invalid input
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
        }
    private static void searchByVendor() {
        System.out.print("Enter the vendor name to search: ");
        String vendorName = keyboard.nextLine().trim();

        System.out.println("Transactions for vendor: " + vendorName);
        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getVendor().equalsIgnoreCase(vendorName)) {
                System.out.printf("%s|%s|%s|%s|%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }
    private static void previousYearReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfPreviousYear = today.minusYears(1).withDayOfYear(1);
        LocalDate endOfPreviousYear = today.minusYears(1).withDayOfYear(today.lengthOfYear());

        System.out.println("Previous Year Transactions:");
        for (Transaction transaction : allTransactions) {
            if (transaction != null && !transaction.getDate().isBefore(startOfPreviousYear) && !transaction.getDate().isAfter(endOfPreviousYear)) {
                System.out.println(transaction);
            }
        }
    }
    private static void yearToDateReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfPreviousYear = today.minusYears(1).withDayOfYear(1);
        LocalDate endOfPreviousYear = today.minusYears(1).withDayOfYear(today.lengthOfYear());

        System.out.println("Previous Year Transactions:");
        for (Transaction transaction : allTransactions) {
            if (transaction != null && !transaction.getDate().isBefore(startOfPreviousYear) && !transaction.getDate().isAfter(endOfPreviousYear)) {
                System.out.println(transaction);
            }
        }
    }

    private static void previousMonthReport() {

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = today.withDayOfMonth(1).minusDays(1);

        System.out.println("Previous Month Transactions:");
        for (Transaction transaction : allTransactions) {
            if (transaction != null && !transaction.getDate().isBefore(firstDayOfPreviousMonth) && !transaction.getDate().isAfter(lastDayOfPreviousMonth)) {
                System.out.println(transaction);
            }
        }
    }
    private static void monthToDateReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);

        System.out.println("Month To Date Transactions:");
        for (Transaction transaction : allTransactions) {
            if (transaction != null && !transaction.getDate().isBefore(startOfMonth) && !transaction.getDate().isAfter(today)) {
                System.out.println(transaction);
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
                    itemFromFile.setDate(LocalDate.parse(values[0]));
                    itemFromFile.setTime(LocalTime.parse(values[1]));
                    itemFromFile.setDescription(values[2]);  // Description is at index 2
                    itemFromFile.setVendor(values[3]);       // Vendor is at index 3
                    itemFromFile.setAmount(Double.parseDouble(values[4].replace("$", "")));
                    // Amount is at index 4

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


