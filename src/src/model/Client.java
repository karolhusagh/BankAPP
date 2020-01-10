package model;

import java.math.BigDecimal;
import controller.LoginSystem;

public class Client {
    private String firstName;
    private String lastName;
    private String id;
    private String password;
    private static Integer accountNumber;
    private BigDecimal balance;

    public Client(String firstName, String lastName, String id, String password, Integer accountNumber, BigDecimal balance){
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
        this.password = LoginSystem.getMd5(password);
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

    public static Integer getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void update(){
        SQLquery.update(this);
    }
}
