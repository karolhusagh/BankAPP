package view;

import model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;


public class AccessAccount extends JFrame {
    //TEST
    public static void main(String[] args) {
        new AccessAccount(2);
    }

    private Connection bankConnection = DatabaseConnection.getConnection();

    //????????????
    private PreparedStatement preparedStatementClient;


    //??????????
    protected static JTextArea results;
    private JTextField input;
    private JPanel panelEAST = new JPanel();
    private JPanel panelTop = new JPanel();




    //???????????
    private int accountNumber;
    private BigDecimal accountBalance;





    public AccessAccount(int accountNumber) {
        this.setAccountNumber(accountNumber);

        createView();

        listClients();

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
        menuBar.setLayout(new GridLayout(0,1));

        panelEAST.add(menuBar);
    }

    private void createPanels() {
        //TOP
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo,JLabel.CENTER);
        panelTop.add(logo);
        panelTop.setBackground(Color.WHITE);
        add(panelTop, BorderLayout.NORTH);

        JButton checkAcctInfo = new JButton("Account Info");
        checkAcctInfo.addActionListener(e -> showBalance());

        JButton deposit = new JButton("Deposit");

        JButton withdrawal = new JButton("Withdrawal");

        JButton transfer = new JButton("New Transfer");
        transfer.addActionListener(e -> new TransferAccount());

        JButton history = new JButton("Show History");
        history.addActionListener(e -> new ShowHistory(accountNumber));

        JButton resetPasswd = new JButton("Reset Password");
        resetPasswd.addActionListener(e -> new ResetPassword());

        JButton clear = new JButton("Clear Window");
        clear.addActionListener(e -> clear());

        JButton logOut = new JButton("Log Out");
        logOut.addActionListener(e -> {
            dispose();
            new LoginAccount();});


        panelEAST.setLayout(new GridLayout(16,1));
        panelEAST.add(checkAcctInfo);
        panelEAST.add(deposit);
        panelEAST.add(withdrawal);
        panelEAST.add(transfer);
        panelEAST.add(history);
        panelEAST.add(resetPasswd);
        panelEAST.add(clear);
        panelEAST.add(logOut);
        panelEAST.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEAST.setBackground(Color.WHITE);

        add(panelEAST, BorderLayout.CENTER);


        TransactionActionListener transactionActionListener = new TransactionActionListener();

        checkAcctInfo.addActionListener(transactionActionListener);
        deposit.addActionListener(transactionActionListener);
        withdrawal.addActionListener(transactionActionListener);

        // MID
        results = new JTextArea(30, 30);
        JPanel panelCenter = new JPanel();
        panelCenter.add(results);
        panelCenter.setBackground(Color.WHITE);

        add(panelCenter, BorderLayout.WEST);

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
        panelBottom.setBackground(Color.WHITE);
        add(panelBottom, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void clear() {
        results.setText("");
    }

    private void createView() {
        setSize(500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createMenu();

        createPanels();
    }


    private void deposit(BigDecimal depositAmount) {

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);


        String updateStatement = "UPDATE Bank SET balance = balance + ? where account_number = ?";

        String depositStatement = "INSERT INTO Transactions (account_number,tran_type, tran_value, tran_date ) values(?,?,?,?)";


        try {

            PreparedStatement preparedStatementDeposit = bankConnection.prepareStatement(depositStatement);

            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            //DEPOSIT
            preparedStatementDeposit.setInt(1, getAccountNumber());
            preparedStatementDeposit.setString(2, "deposit");
            preparedStatementDeposit.setBigDecimal(3, depositAmount);
            preparedStatementDeposit.setString(4,currentTime);

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

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        String updateStatement = "UPDATE Bank SET balance = balance - ? where account_number = ?";

        String withdrawalStatement = "INSERT INTO Transactions (account_number, tran_type, tran_value, tran_date ) values(?,?,?,?)";

        try {
            PreparedStatement preparedStatementWithdrawal = bankConnection.prepareStatement(withdrawalStatement);
            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            //WITHDRAWAL
            preparedStatementWithdrawal.setInt(1, getAccountNumber());
            preparedStatementWithdrawal.setString(2, "withdrawal");
            preparedStatementWithdrawal.setBigDecimal(3, withdrawlAmount);
            preparedStatementWithdrawal.setString(4, currentTime);

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


