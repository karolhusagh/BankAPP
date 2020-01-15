package view;

import model.Client;
import model.SQLquery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import static java.math.RoundingMode.DOWN;


class AccessAccount extends JFrame {
    private Client client;
    private static JTextArea results;
    private JTextField input;
    private JPanel panelEAST = new JPanel();
    private JPanel panelTop = new JPanel();

    AccessAccount(int accountNumber) {
        client = SQLquery.selectCustomer(accountNumber);

        createView();

        results.append("Client info: ");
        results.append(client.getFirstName() + " " + client.getLastName() + "\n" +
                "Account number: " + client.getAccountNumber() + "\n");

    }

    private void createView() {
        setSize(500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Account: " + client.getAccountNumber());

        createMenu();

        createPanels();
    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(0, 1));

        panelEAST.add(menuBar);
    }

    private void createPanels() {
        //TOP
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo, JLabel.CENTER);
        panelTop.add(logo);
        panelTop.setBackground(Color.WHITE);
        add(panelTop, BorderLayout.NORTH);

        JButton checkAcctInfo = new JButton("Balance");
        checkAcctInfo.addActionListener(e -> showBalance());

        JButton deposit = new JButton("Deposit");

        JButton withdrawal = new JButton("Withdrawal");

        JButton transfer = new JButton("New Transfer");
        transfer.addActionListener(e -> new TransferAccount(client));

        JButton history = new JButton("Show History");
        history.addActionListener(e -> new ShowHistory(client.getAccountNumber()));

        JButton resetPasswd = new JButton("Reset Password");
        resetPasswd.addActionListener(e -> new ResetPassword(client));

        JButton clear = new JButton("Clear Window");
        clear.addActionListener(e -> clear());

        JButton logOut = new JButton("Log Out");
        logOut.addActionListener(e -> {
            dispose();
            new LoginAccount();
        });


        panelEAST.setLayout(new GridLayout(16, 1));
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
            try {
                if (TransactionActionListener.actionPerformed.equals("Deposit")) {
                    client.deposit(new BigDecimal(input.getText().trim().replace(",", ".")).setScale(2, DOWN));
                }
                if (TransactionActionListener.actionPerformed.equals("Withdrawal"))
                    client.withdrawal((new BigDecimal(input.getText().trim().replace(",", ".")).setScale(2, DOWN)));
                if (TransactionActionListener.actionPerformed.equals(""))
                    results.append("Please make a selection above \n");
            }catch (NumberFormatException NFE){
                JOptionPane.showMessageDialog(null,"Enter proper value!");
            }
        });


        JPanel panelBottom = new JPanel();
        panelBottom.add(input);
        panelBottom.add(submit);
        panelBottom.setBackground(Color.WHITE);
        add(panelBottom, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void showBalance() {
        results.append("Balance: " + client.getBalance() + "\n");
    }

    private void clear() {
        results.setText("");
        results.append("Client info: ");
        results.append(client.getFirstName() + " " + client.getLastName() + "\n" +
                "Account number: " + client.getAccountNumber() + "\n");
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