package model;

import javax.swing.*;
import java.math.BigDecimal;

public class Client {
    private String firstName;
    private String lastName;
    private String id;
    private String password;
    private static Integer accountNumber;
    private BigDecimal balance;

    Client(String firstName, String lastName, String id, String password, Integer accountNumber, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        Client.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private void update() {
        SQLquery.update(this);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        SQLquery.addTransaction(balance, "deposit", accountNumber);
        update();
    }

    public void withdrawal(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            JOptionPane.showMessageDialog(null, "Insufficient funds!!!");
            return;
        }
        balance = balance.subtract(amount);
        SQLquery.addTransaction(balance, "withdrawal", accountNumber);
        update();
    }

    public void changePassword(String update) {
        password = update;
        SQLquery.ChangePassword(update, this);
    }

    public void transaction(Integer toNumber, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            JOptionPane.showMessageDialog(null, "Insufficient funds!!!");
            return;
        }

        SQLquery.transaction(toNumber, amount);
        SQLquery.addTransaction(amount, "transaction to " + toNumber, accountNumber);
        SQLquery.addTransaction(amount, "transaction from " + accountNumber, toNumber);
        balance = balance.subtract(amount);
        update();
    }
}