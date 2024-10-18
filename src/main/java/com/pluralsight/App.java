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

                    logger.write(formattedDate + "|" + formattedTime + "|" +description +"|"+vendor+"|"+ amount + "|$" ); // add description today the csv string


                    logger.newLine();

                    Transaction newTransaction = new Transaction();
                    newTransaction.setTime(LocalTime.parse(formattedTime));
                    newTransaction.setDate(LocalDate.parse(formattedDate));
                    newTransaction.setDescription(description);
                    newTransaction.setVendor(vendor);
                    newTransaction.setAmount(amount);

                    allTransactions.add(newTransaction);

                } else if (checkOrcash.equalsIgnoreCase("check")) {

                    System.out.print("Please enter the pay to the order of: ");
                    String payTo = keyboard.nextLine();
                    System.out.print("Please enter the amount written on the check: $");
                    Double amountOnCheck = Double.parseDouble(keyboard.nextLine());


                    String input = depositFormat(payTo,amountOnCheck);

                    logger.write(today + input);

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

            logger.write(formattedDate + "|" + formattedTime + "|" +"|"+description+ "|"+ vendor+"|$" +-amountDue); // add description today the csv string
                logger.newLine();


            System.out.println("Payment made successfully");
            logger.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

        public static void displayLedger() {

            System.out.println("1) All");
            System.out.println("2) Deposits");
            System.out.println("3) Payments");
            System.out.println("4) Reports");
            System.out.println("5) Home");
            System.out.print("Select an option: ");

            boolean ledgerMenu = true;

            do{


                int opt = keyboard.nextInt();
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
            }while (ledgerMenu);
        }

    public static void allEntries() {
        // Loop through the already loaded 'allTransactions' list instead of reloading it
        for (Transaction transaction : allTransactions) {
            if (transaction != null) {
                System.out.println(transaction);
            }
        }
    }

    public static void deposits() {
        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }

    public static void payments() {
        for (Transaction transaction : allTransactions) {
            if (transaction != null && transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }


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

//            String opt = keyboard.nextLine();
//            boolean option;
//            switch (option){
                //case 1:

            }
            }

        //}


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
                    itemFromFile.setDescription(values[2]);  // Description is at index 2
                    itemFromFile.setVendor(values[3]);       // Vendor is at index 3
                    itemFromFile.setAmount(Double.parseDouble(values[4]));  // Amount is at index 4


                    //add it to the transaction arraylist
                    transactions.add(itemFromFile);
                }

        } catch(Exception exp){
            System.out.println(exp.getLocalizedMessage());
        }

        return transactions;
    }


    }


