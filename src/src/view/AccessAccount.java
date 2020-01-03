package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;


public class AccessAccount extends JFrame {
    //TEST
    public static void main(String[] args) {
        new AccessAccount(9);
    }

    private Connection bankConnection;
    private PreparedStatement preparedStatementClient;

    protected static JTextArea results;
    private JTextField input;
    private JPanel panelTop = new JPanel();

    private int accountNumber;
    private BigDecimal accountBalance;

    public AccessAccount(int accountNumber) {
        this.setAccountNumber(accountNumber);

        createView();

        connectToDatabase();

        listClients();

    }

    private void connectToDatabase() {
        bankConnection = new DatabaseConnection().DatabaseConnection();
    }

    private void showBalance() {

        String accountInfoStatement = "SELECT balance from Bank where account_number = ?";

        try {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setInt(1, getAccountNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) // first row
            {
                AccessAccount.results.append(String.format("Balance: %.2f \n", resultSet.getBigDecimal(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void listClients() {
        try {

            String selectCustomers = "SELECT first_name, last_name, account_number FROM Bank where account_number = ?";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(selectCustomers);

            preparedStatement.setInt(1, getAccountNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                results.append("Client info: ");
                results.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n" +
                        "Account number: " + resultSet.getString(3) + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        panelTop.add(menuBar);
    }

    private void createPanels() {
        //TOP

        JButton checkAcctInfo = new JButton("Account Info");
        checkAcctInfo.addActionListener(e -> showBalance());

        JButton deposit = new JButton("Deposit");

        JButton withdrawal = new JButton("Withdrawal");

        JButton logOut = new JButton("Log Out");

        logOut.addActionListener(e ->
        {
            results.append("Logging out of account");
            try {
                Thread.sleep(2000);
                dispose();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        });

        panelTop.add(checkAcctInfo);
        panelTop.add(deposit);
        panelTop.add(withdrawal);
        panelTop.add(logOut);

        add(panelTop, BorderLayout.NORTH);


        TransactionActionListener transactionActionListener = new TransactionActionListener();

        checkAcctInfo.addActionListener(transactionActionListener);
        deposit.addActionListener(transactionActionListener);
        withdrawal.addActionListener(transactionActionListener);
        logOut.addActionListener(transactionActionListener);

        // MID
        results = new JTextArea(30, 30);
        JPanel panelCenter = new JPanel();
        panelCenter.add(results);

        add(panelCenter, BorderLayout.CENTER);

        // BOTTOM
        input = new JTextField(15);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e ->
        {
            if (TransactionActionListener.actionPerformed.equals("Deposit"))
                deposit((new BigDecimal(input.getText())));
            if (TransactionActionListener.actionPerformed.equals("Withdrawal"))
                areEnoughFundsAvailable();
            if (TransactionActionListener.actionPerformed.equals(""))
                results.append("Please make a selection above \n");
        });


        JPanel panelBottom = new JPanel();
        panelBottom.add(input);
        panelBottom.add(submit);
        add(panelBottom, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void createView() {
        setSize(650, 600);
        setTitle("Access account");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createMenu();

        createPanels();
    }


    private void deposit(BigDecimal depositAmount) {
        int Transnum = 1; // do zrobienia zeby liczylo

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);


        String updateStatement = "UPDATE Bank SET balance = balance + ? where account_number = ?";

        String depositStatement = "INSERT INTO Transactions (account_number,tran_num,tran_type, tran_value, tran_date ) values(?,?,?,?,?)";


        try {

            PreparedStatement preparedStatementDeposit = bankConnection.prepareStatement(depositStatement);

            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            //DEPOSIT
            preparedStatementDeposit.setInt(1, getAccountNumber());
            preparedStatementDeposit.setInt(2, 1);
            preparedStatementDeposit.setString(3, "deposit");
            preparedStatementDeposit.setBigDecimal(4, depositAmount);
            preparedStatementDeposit.setString(5,currentTime);

            //UPDATE
            preparedStatementUpdate.setBigDecimal(1, depositAmount);
            preparedStatementUpdate.setInt(2, getAccountNumber());

            preparedStatementUpdate.execute();
            preparedStatementDeposit.execute();

            input.setText(" ");

            results.append(String.format("$ %.2f deposited into account # %d \n", depositAmount, getAccountNumber()));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    private void areEnoughFundsAvailable() {

        BigDecimal amountToWithdraw = new BigDecimal(input.getText());

        String checkFunds = "SELECT balance FROM Bank WHERE account_number = ?";

        try {
            preparedStatementClient = bankConnection.prepareStatement(checkFunds);

            preparedStatementClient.setInt(1, getAccountNumber());

            ResultSet balanceResultSet = preparedStatementClient.executeQuery();

            while (balanceResultSet.next()) setAccountBalance(balanceResultSet.getBigDecimal(1));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        if (amountToWithdraw.compareTo(getAccountBalance()) <= 0) {
            withdrawal(amountToWithdraw);
        } else results.append("Sorry, you don't have enough funds \n");
    }


    private void withdrawal(BigDecimal withdrawlAmount) {

        int Transnum = 1; //cza zrobic by zliczalo
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        String updateStatement = "UPDATE Bank SET balance = balance - ? where account_number = ?";

        String withdrawalStatement = "INSERT INTO Transactions (account_number,tran_num,tran_type, tran_value, tran_date ) values(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatementWithdrawal = bankConnection.prepareStatement(withdrawalStatement);
            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            //WITHDRAWAL
            preparedStatementWithdrawal.setInt(1, getAccountNumber());
            preparedStatementWithdrawal.setInt(2, Transnum);
            preparedStatementWithdrawal.setString(3, "withdrawal");
            preparedStatementWithdrawal.setBigDecimal(4, withdrawlAmount);
            preparedStatementWithdrawal.setString(5, currentTime);

            //UPDATE
            preparedStatementUpdate.setBigDecimal(1, withdrawlAmount);
            preparedStatementUpdate.setInt(2, getAccountNumber());

            //Update checking account then make withdrawal
            preparedStatementUpdate.execute();
            preparedStatementWithdrawal.execute();

            results.append(String.format("$ %.2f withdrawn from account # %d \n", withdrawlAmount, getAccountNumber()));

            input.setText("");


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }


    static class TransactionActionListener implements ActionListener {
        static String actionPerformed = "";

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Deposit")) {
                AccessAccount.results.append("Enter amount to deposit" + "\n");
                actionPerformed = "Deposit";
            }
            if (e.getActionCommand().equals("Withdrawal")) {
                AccessAccount.results.append("Enter amount to withdraw" + "\n");
                actionPerformed = "Withdrawal";
            }
        }
    }
}


