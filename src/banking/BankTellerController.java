package banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
Handles all of the event handlers
Handles all exceptions and invalid data.
@author Dharma Wijesinghe, Min Sun You
*/
public class BankTellerController {     //this class is similar to bankteller class (handles commands).
//The GUI replaces the BankTeller class in project 2
//fx:controller="banking.BankTellerController" --> "controller is defined here, this is your controller"
    private AccountDatabase database;
    private static final int ACCOUNT_NOT_FOUND = -1;
    private static final int INVALID_CODE = -1;
    private static final int PLACE_HOLDER_VALUE = 0;
    private static final int NEW_BRUN_CODE = 0;
    private static final int NEWARK_CODE = 1;
    private static final int CAMDEN_CODE = 2;
    private static final int MIN_DEPOSIT = 2500;
    private static final int LOYAL_CODE = 1;
    private static final int NON_LOYAL_CODE = 0;
    private static final int INVALID_LOYAL_CODE = -1;
    private static final int BEGIN_INDEX = 1;
    
    /**
    A default constructor to initialize the AccountDatabase object.
    */
    public BankTellerController() {
        database = new AccountDatabase();
    }
    
    @FXML
    private RadioButton checking;

    @FXML
    private TextField amountDW;

    @FXML
    private RadioButton buttonCamden;

    @FXML
    private RadioButton buttonLoyal;
    
    @FXML
    private RadioButton buttonNonLoyal;
    
    @FXML
    private RadioButton buttonNB;

    @FXML
    private RadioButton buttonNW;

    @FXML
    private ToggleGroup campus;

    @FXML
    private RadioButton checkingDW;

    @FXML
    private RadioButton collegeChecking;

    @FXML
    private RadioButton collegeCheckingDW;

    @FXML
    private DatePicker dob;

    @FXML
    private DatePicker dobDW;

    @FXML
    private TextField firstName;

    @FXML
    private TextField firstNameDW;

    @FXML
    private TextField initialDeposit;

    @FXML
    private TextField lastName;

    @FXML
    private TextField lastNameDW;

    @FXML
    private RadioButton moneyMarket;

    @FXML
    private RadioButton moneyMarketDW;

    @FXML
    private RadioButton savings;

    @FXML
    private RadioButton savingsDW;

    @FXML
    private TextArea textArea;

    @FXML
    private ToggleGroup type;

    @FXML
    private ToggleGroup type1;
    
    /**
    Disables the campus code buttons
    */
    private void disableCampusCodes() {
        buttonNB.setDisable(true);
        buttonNW.setDisable(true);
        buttonCamden.setDisable(true);
    }
    
    /**
     * Enables the campus code buttons on the GUI.
     */
    private void enableCampusCodes() {
        buttonNB.setDisable(false);
        buttonNW.setDisable(false);
        buttonCamden.setDisable(false);
    }
    
    /**
    Disables the loyal button options.
    */
    private void disableLoyalOption() {
        buttonLoyal.setDisable(true);
        buttonNonLoyal.setDisable(true);
    }
    
    /**
     * Takes in a String containing a date in YYYY-MM-DD format and converts it into a Date object.
     * @param date String representation of a date in YYYY-MM-DD format.
     * @return A Date object
     */
    private Date getDate(String date) {
        String[] dateArray = date.split("-");
        return new Date(dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0]);
    }
    
    /**
     * Checks the user inputs from the Deposit and Withdraw tab to ensure they are all valid.
     * @throws Exception
     */
    private void checkDWInputs(String operation) throws Exception {
        if(firstNameDW.getText().isEmpty() || lastNameDW.getText().isEmpty() || dobDW.getValue() == null || amountDW.getText().isEmpty()) {
            throw new Exception("Missing data for depositing or withdrawing.");
        }
        Date dob = getDate(dobDW.getValue().toString());
        if(!dob.isValid() || dob.compareTo(new Date()) >= 0) {
            throw new Exception("Date of birth invalid.");
        }
        double amount = Double.parseDouble(amountDW.getText());
        if(amount <= 0) {
        	if(operation.equals("Deposit")) {
        		throw new Exception("Deposit - amount cannot be 0 or negative.");
        	}
        	else if(operation.equals("Withdraw")) {
        		throw new Exception("Withdraw - amount cannot be 0 or negative.");
        	}
        	else {
        		throw new Exception("Amount cannot be 0 or negative.");
        	}
        }
    }
    
    /**
     * Checks to see if the account for the given parameters exist in the database, throwing an exception if it doesn't.
     * @param customer contains the profile for the account creator
     * @param amount contains the amount of money being deposited/withdrawn
     * @param accountType contains a String representation of the account type.
     * @throws Exception
     */
    private void checkDWAccount(Profile customer, double amount, String accountType) throws Exception {
        if(accountType.equals("Checking")) {
            Account checkingAccount = new Checking(customer, amount);
            if(database.findAcc(checkingAccount) == ACCOUNT_NOT_FOUND){
                throw new Exception(firstNameDW.getText() + " " + lastNameDW.getText() + " " + getDate(dobDW.getValue().toString()).toString() + " Checking" + " is not in the database.");
            }
        } else if(accountType.equals("CollegeChecking")) {
            Account collegeCheckingAccount = new CollegeChecking(customer, amount);
            if(database.findAcc(collegeCheckingAccount) == ACCOUNT_NOT_FOUND) {
                throw new Exception(firstNameDW.getText() + " " + lastNameDW.getText() + " " + getDate(dobDW.getValue().toString()).toString() + " College Checking" + " is not in the database.");
            }
        } else if(accountType.equals("Savings")) {
            Account savingsAccount = new Savings(customer, amount);
            if(database.findAcc(savingsAccount) == ACCOUNT_NOT_FOUND) {
                throw new Exception(firstNameDW.getText() + " " + lastNameDW.getText() + " " + getDate(dobDW.getValue().toString()).toString() + " Savings" + " is not in the database.");
            }
        } else if(accountType.equals("MoneyMarket")) {
            Account moneyMarketAccount = new MoneyMarket(customer, amount);
            if(database.findAcc(moneyMarketAccount) == ACCOUNT_NOT_FOUND) {
                throw new Exception(firstNameDW.getText() + " " + lastNameDW.getText() + " " + getDate(dobDW.getValue().toString()).toString() + " Money Market" + " is not in the database.");
            }
        }
    }
    
    /**
    Enables the loyal button options.
    */
    private void enableLoyalOption() {
        buttonLoyal.setDisable(false);
        buttonNonLoyal.setDisable(false);        
    }
    
    /**
    Disables button for campus codes and loyal options.
    @param event the ActionEvent
    */
    @FXML
    void checkingPressed(ActionEvent event) {
        disableCampusCodes();
        disableLoyalOption();
    }

    /**
    Enables campus codes
    Disables loyalty options
    @param event the ActionEvent
    */
    @FXML
    void collegeCheckingPressed(ActionEvent event) {
        enableCampusCodes();
        disableLoyalOption();
    }
    
    /**
    Disables campus codes.
    Enables loyalty option.
    @param event the ActionEvent
    */
    @FXML
    void savingsPressed(ActionEvent event) {
        disableCampusCodes();
        enableLoyalOption();
    }
    
    /**
    Disables campus codes and loyalty option buttons.
    @param event the ActionEvent
    */
    @FXML
    void moneyMarketPressed(ActionEvent event) {
        disableCampusCodes();
        disableLoyalOption();
    }
    
    /**
     * Uses the user inputs to find the corresponding account and update the account balance.
     * @param event the ActionEvent which is triggered by the Deposit button.
     */
    @FXML
    void deposit(ActionEvent event) {
        try {
            checkDWInputs("Deposit");
            Date dob = getDate(dobDW.getValue().toString());
            double depositAmount = Double.parseDouble(amountDW.getText());
            String firstName = firstNameDW.getText().substring(0, 1).toUpperCase() + firstNameDW.getText().substring(1).toLowerCase();
            String lastName = lastNameDW.getText().substring(0, 1).toUpperCase() + lastNameDW.getText().substring(1).toLowerCase();
            Profile customer = new Profile(firstName, lastName, dob);
            if(checkingDW.isSelected()) {
                checkDWAccount(customer, depositAmount, "Checking");
                Account checkingAccount = new Checking(customer, depositAmount);
                database.deposit(checkingAccount);
            } else if(collegeCheckingDW.isSelected()) {
                checkDWAccount(customer, depositAmount, "CollegeChecking");
                Account collegeCheckingAccount = new CollegeChecking(customer, depositAmount);
                database.deposit(collegeCheckingAccount);
            } else if(savingsDW.isSelected()) {
                checkDWAccount(customer, depositAmount, "Savings");
                Account savingsAccount = new Savings(customer, depositAmount);
                database.deposit(savingsAccount);
            } else if(moneyMarketDW.isSelected()) {
                checkDWAccount(customer, depositAmount, "MoneyMarket");
                Account moneyMarketAccount = new MoneyMarket(customer, depositAmount);
                database.deposit(moneyMarketAccount);
            }
            textArea.appendText("Deposit - balance updated.\n");
        }
        catch(NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
     * Uses the user's input to withdraw from the specified account and update the balance.
     * @param event the ActionEvent which is triggered by the withdraw button.
     */
    @FXML
    void withdraw(ActionEvent event) {
        try {
            checkDWInputs("Withdraw");
            Date dob = getDate(dobDW.getValue().toString());
            double withdrawAmount = Double.parseDouble(amountDW.getText());
            String firstName = firstNameDW.getText().substring(0, 1).toUpperCase() + firstNameDW.getText().substring(1).toLowerCase();
            String lastName = lastNameDW.getText().substring(0, 1).toUpperCase() + lastNameDW.getText().substring(1).toLowerCase();
            Profile customer = new Profile(firstName, lastName, dob);
            if(checkingDW.isSelected()) {
                checkDWAccount(customer, withdrawAmount, "Checking");
                Account checkingAccount = new Checking(customer, withdrawAmount);
                if(database.withdraw(checkingAccount) == false) {
                    throw new Exception("Withdraw - insufficient fund.");
                }
            } else if(collegeCheckingDW.isSelected()) {
                checkDWAccount(customer, withdrawAmount, "CollegeChecking");
                Account collegeCheckingAccount = new CollegeChecking(customer, withdrawAmount);
                if(database.withdraw(collegeCheckingAccount) == false) {
                    throw new Exception("Withdraw - insufficient fund.");
                }
            } else if(savingsDW.isSelected()) {
                checkDWAccount(customer, withdrawAmount, "Savings");
                Account savingsAccount = new Savings(customer, withdrawAmount);
                if(database.withdraw(savingsAccount) == false) {
                    throw new Exception("Withdraw - insufficient fund.");
                }
            } else if(moneyMarketDW.isSelected()) {
                checkDWAccount(customer, withdrawAmount, "MoneyMarket");
                Account moneyMarketAccount = new MoneyMarket(customer, withdrawAmount);
                if(database.withdraw(moneyMarketAccount) == false) {
                    throw new Exception("Withdraw - insufficient fund.");
                }
            }
            textArea.appendText("Withdraw - balance updated.\n");
        }
        catch(NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Prints all the accounts in current order.
    @param event the ActionEvent.
    */
    @FXML
    void displayAllAccounts(ActionEvent event) { //check if this is the right method
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            textArea.appendText("*list of accounts in the database*\n");
            String aggregateOutput = database.print();
            aggregateOutput = aggregateOutput.substring(BEGIN_INDEX);            
            String[] aggregateOutputList = aggregateOutput.split("\\+");  
            for(int i = 0; i < aggregateOutputList.length; i++) {
                textArea.appendText(aggregateOutputList[i] + "\n");
            }
            textArea.appendText("*end of list*\n");
        }
        catch(Exception e){
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Prints all accounts ordered by type.
    @param event the ActionEvent.
    */
    @FXML
    void displayByType(ActionEvent event) {
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            textArea.appendText("*list of accounts by account type.\n");
            String aggregateOutput = database.printByAccountType();
            aggregateOutput = aggregateOutput.substring(BEGIN_INDEX);
            String[] aggregateOutputList = aggregateOutput.split("\\+");
            for(int i = 0; i < aggregateOutputList.length; i++) {
                textArea.appendText(aggregateOutputList[i] + "\n");
            }           
            textArea.appendText("*end of list.\n");
        }
        catch(Exception e){
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Prints all accounts with fees and monthly interest.
    @param event the ActionEvent.
    */
    @FXML
    void displayWithDetails(ActionEvent event) {
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            textArea.appendText("*list of accounts with fee and monthly interest\n");
            String aggregateOutput = database.printFeeAndInterest();
            aggregateOutput = aggregateOutput.substring(BEGIN_INDEX);
            String[] aggregateOutputList = aggregateOutput.split("\\+");
            for(int i = 0; i < aggregateOutputList.length; i++) {
                textArea.appendText(aggregateOutputList[i] + "\n");
            }                       
            textArea.appendText("*end of list.\n");
        }
        catch(Exception e){
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    
    /**
    Updates all accounts and displays them
    @param event the ActionEvent.
    */
    @FXML
    void updateDisplay(ActionEvent event) {
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            database.updateBalances();
            textArea.appendText("*list of accounts with updated balance\n");
            String aggregateOutput = database.print();
            aggregateOutput = aggregateOutput.substring(BEGIN_INDEX);
            String[] aggregateOutputList = aggregateOutput.split("\\+");
            for(int i = 0; i < aggregateOutputList.length; i++) {
                textArea.appendText(aggregateOutputList[i] + "\n");
            }
            textArea.appendText("*end of list.\n");
        }
        catch(Exception e){
            textArea.appendText(e.getMessage() + "\n");
        }
    }

    /**
    Formats the date properly to pass to Date constructor.
    @return String the formatted date.
    */
    private String formatDate() {
        String inputDate = dob.getValue().toString();
        String[] dateComponents = inputDate.split("-");
        return dateComponents[1] + "/" + dateComponents[2] + "/" + dateComponents[0]; 
    }
    
    /**
    Opens an account with given details.
    @param event ActionEvent.
    */
    @FXML
    void openAccount(ActionEvent event) {
        try {
            //check if 3 fundamental requirements are msising
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty() || dob.getValue() == null || initialDeposit.getText().isEmpty()) {
                throw new Exception("Missing data for opening an account.");
            }
            String givenDate = formatDate();
            String firstNameInput = firstName.getText().substring(0,1).toUpperCase() + firstName.getText().substring(1).toLowerCase();
            String lastNameInput = lastName.getText().substring(0,1).toUpperCase() + lastName.getText().substring(1).toLowerCase();
            if(checking.isSelected()) {
                openCheckingAccount(firstNameInput, lastNameInput, givenDate);
            }
            else if(collegeChecking.isSelected()) {
                openCollegeCheckingAccount(firstNameInput, lastNameInput, givenDate);
            }
            else if(savings.isSelected()) {
                openSavingsAccount(firstNameInput, lastNameInput, givenDate);
            }
            else if(moneyMarket.isSelected()) {
                openMMAccount(firstNameInput, lastNameInput, givenDate);
            }
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Opens a checking account.
    @param firstNameCap the given first name capitalized
    @param lastNameCap the given last name capitalized
    @param givenDate the given date formatted
    */
    private void openCheckingAccount(String firstNameCap, String lastNameCap, String givenDate) {
        try{
            //missing data taken care of by caller method.
            Date customerDob = new Date(givenDate);
            if(!customerDob.isValid() || customerDob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            double inputBalance = Double.parseDouble(initialDeposit.getText());
            if(inputBalance <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            Profile customer = new Profile(firstNameCap, lastNameCap, customerDob);
            Account newCheckingAccount = new Checking(customer, false, inputBalance);
            Account testCollegeCheckingAccount = new CollegeChecking(customer, PLACE_HOLDER_VALUE);
            if(database.findAcc(testCollegeCheckingAccount) != ACCOUNT_NOT_FOUND) {
                //Customer already has a college checking account, so a checking account cannot be added.
                throw new Exception(customer.toString() + " same account(type) is in the database.");
            } else if(database.findAcc(newCheckingAccount) == ACCOUNT_NOT_FOUND) {
                database.open(newCheckingAccount);
                textArea.appendText("Account opened.\n");
            } else { //Checking account is already in the system, may be open or closed.
                if(database.open(newCheckingAccount)) {
                    textArea.appendText("Account reopened.\n");
                } else { //Checking account is already open.
                    throw new Exception(customer.toString() + " same account(type) is in the database.");
                }
            }           
        }
        catch(NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        } 
    }
    
    /**
    Returns the corresponding campus code.
    @return int a campus code.
    */
    private int getCorrespondingCampusCode() {
        if(buttonNB.isSelected()) {
            return NEW_BRUN_CODE; 
        }
        if(buttonNW.isSelected()) {
            return NEWARK_CODE;
        }
        if(buttonCamden.isSelected()) {
            return CAMDEN_CODE;
        }
        else {
            return INVALID_CODE;
        }
    }
    
    /**
    Opens a college checking account.
    @param firstNameCap the given first name capitalized
    @param lastNameCap the given last name capitalized
    @param givenDate the given date formatted
    */
    private void openCollegeCheckingAccount(String firstNameCap, String lastNameCap, String givenDate) {
        try {
            if(!buttonNB.isSelected() && !buttonNW.isSelected() && !buttonCamden.isSelected()) {
                throw new Exception("Missing data for opening an account.");
            }
            Date customerDob = new Date(givenDate);
            if(!customerDob.isValid() || customerDob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            double inputBalance = Double.parseDouble(initialDeposit.getText());
            if(inputBalance <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            int campusCode = getCorrespondingCampusCode();
            if(!(campusCode >= NEW_BRUN_CODE && campusCode <= CAMDEN_CODE)) {
                throw new Exception("Invalid campus code.");
            }
            Profile customer = new Profile(firstNameCap, lastNameCap, customerDob);
            Account testCheckingAccount = new Checking(customer, PLACE_HOLDER_VALUE);
            Account newCollegeCheckingAccount = new CollegeChecking(customer, false, inputBalance, campusCode);
            if(database.findAcc(testCheckingAccount) != ACCOUNT_NOT_FOUND) {
                throw new Exception(customer.toString() + " same account(type) is in the database.");
            } else if(database.findAcc(newCollegeCheckingAccount) == ACCOUNT_NOT_FOUND) {
                database.open(newCollegeCheckingAccount);
                textArea.appendText("Account opened.\n");
            } else { //College checking account is already in the system, may be open or closed.
                if(database.open(newCollegeCheckingAccount)) {
                    textArea.appendText("Account reopened.\n");
                } else { //College Checking account is already open.
                    throw new Exception(customer.toString() + " same account(type) is in the database.");
                }
            }
        }
        catch(NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }

    /**
    Returns the loyalty code.
    @return int the corresponding number for loyalty status.
    */
    private int loyalCode() {
        if(buttonLoyal.isSelected()) {
            return LOYAL_CODE;
        }
        else if(buttonNonLoyal.isSelected()) {
            return NON_LOYAL_CODE;
        }
        return INVALID_LOYAL_CODE;
    }
    
    /**
    Opens a savings account.
    @param firstNameCap the given first name capitalized
    @param lastNameCap the given last name capitalized
    @param givenDate the given date formatted
    */
    private void openSavingsAccount(String firstNameCap, String lastNameCap, String givenDate) {
        try {
            if(!buttonLoyal.isSelected() && !buttonNonLoyal.isSelected()) {
                throw new Exception("Missing data for opening an account.");
            }
            Date customerDob = new Date(givenDate);
            if(!customerDob.isValid() || customerDob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            if(Double.parseDouble(initialDeposit.getText()) <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            //Finding the account
            Profile holder = new Profile(firstNameCap, lastNameCap, customerDob);
            Account acc = new Savings(holder, false, Double.parseDouble(initialDeposit.getText()), loyalCode());
            if(database.findAcc(acc) != ACCOUNT_NOT_FOUND) {// acc exists
                if(database.isClosed(acc)) { //is it closed?
                    database.open(acc); //reopen
                    textArea.appendText("Account reopened.\n");
                }
                else { //it's already open
                    throw new Exception(acc.holder.toString() + " same account(type) is in the database.");    
                }
            }
            else {//account not found
                database.open(acc);
                textArea.appendText("Account opened.\n");
            }
        }
        catch(NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Opens a money market account.
    @param firstNameCap the given first name capitalized
    @param lastNameCap the given last name capitalized
    @param givenDate the given date formatted
    */
    private void openMMAccount(String firstNameCap, String lastNameCap, String givenDate) {
        try {
            Date customerDOB = new Date(givenDate);
            if(!customerDOB.isValid() || customerDOB.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            if(Double.parseDouble(initialDeposit.getText()) <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            if(Double.parseDouble(initialDeposit.getText()) < MIN_DEPOSIT) {
                throw new Exception("Minimum of $2500 to open a MoneyMarket account.");
            }
            //Finding the account
            Profile holder = new Profile(firstNameCap, lastNameCap, customerDOB);
            Account acc = new MoneyMarket(holder, false, Double.parseDouble(initialDeposit.getText()));
            if(database.findAcc(acc) != ACCOUNT_NOT_FOUND) {// acc exists
                if(database.isClosed(acc)) { //is it closed?
                    database.open(acc); //reopen
                    textArea.appendText("Account reopened.\n");
                }
                else { //it's already open
                    throw new Exception(acc.holder.toString() + " same account(type) is in the database.");    
                }
            }
            else {//account not found
                database.open(acc);
                textArea.appendText("Account opened.\n");
            }
        }
        catch(NumberFormatException e) {
            textArea.appendText("Not a valid amount.\n");
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Reformats the given date before calling method to close the account
    @param event the ActionEvent.
    */
    @FXML
    void closeAccount(ActionEvent event) {
        try {
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty() || dob.getValue() == null) {
                throw new Exception("Missing data for closing an account.");
            }
            String givenDate = formatDate();
            String firstNameInput = firstName.getText().substring(0,1).toUpperCase() + firstName.getText().substring(1).toLowerCase();
            String lastNameInput = lastName.getText().substring(0,1).toUpperCase() + lastName.getText().substring(1).toLowerCase();
            closeAccountMethod(firstNameInput, lastNameInput, givenDate);
        }
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Closes the account with given details.
    @param firstNameCap the given first name capitalized
    @param lastNameCap the given last name capitalized
    @param givenDate the given date formatted
    */
    private void closeAccountMethod(String firstNameCap, String lastNameCap, String givenDate) {
        try {
            Date customerDOB = new Date(givenDate);
            if(!customerDOB.isValid()) {
                throw new Exception("Date of birth invalid.");
            }
            Profile profile = new Profile(firstNameCap, lastNameCap, customerDOB);
            Account matchingAcc = createTempAcc(profile, PLACE_HOLDER_VALUE);
            if(database.findAcc(matchingAcc) == ACCOUNT_NOT_FOUND) {
                throw new Exception("Account not in database.");
            }
            if(database.close(matchingAcc)) {
                textArea.appendText("Account closed.\n");
            }
            else {
                throw new Exception("Account is closed already.");
            }
        }  
        catch(Exception e) {
            textArea.appendText(e.getMessage() + "\n");
        }
    }
    
    /**
    Creates a temporary account
    @param holder the Profile of account
    @param amount the balance
    @return Account the temporary account
    */
    private Account createTempAcc(Profile holder, double amount) {
        Account tempAcc = null;
        String type = typeOfAccount();
        if(type.equals("Checking")) {
            tempAcc = new Checking(holder, amount); //amount set to 0 since its not useful here
        }
        else if(type.equals("CollegeChecking")) {
            tempAcc = new CollegeChecking(holder, amount);
        }
        else if(type.equals("Savings")){
            tempAcc = new Savings(holder, amount);
        }
        else if(type.equals("MoneyMarket")) {
            tempAcc = new MoneyMarket(holder, amount);
        }
        return tempAcc;
    }
    
    /**
    Returns the type of account.
    @return String the type of account.
    */
    private String typeOfAccount() {
        if(checking.isSelected()) {
            return "Checking";
        }
        else if(collegeChecking.isSelected()) {
            return "CollegeChecking";
        }
        else if(savings.isSelected()) {
            return "Savings";
        }   
        else if(moneyMarket.isSelected()) {
            return "MoneyMarket";
        }
        return null;
    }
}