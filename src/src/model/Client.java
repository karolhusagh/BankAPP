package model;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class Client {
    private String firstName;
    private String lastName;
    private String id;
    private String password;
    private String accountNumber;
    private BigDecimal balance;

    public Client(String firstName, String lastName, String id, String password, String accountNumber, BigDecimal balance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
