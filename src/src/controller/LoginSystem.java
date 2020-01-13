package controller;

import model.Client;
import view.AccessAccount;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginSystem {
    public static String getMd5(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            return no.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean checkPasswd(String login, String passwd){
        if(Boolean.valueOf(Objects.equals(SQLquery.getPassword(login), getMd5(passwd))==true)){
            SQLquery.selectCustomer(login);
            new AccessAccount(Client.getAccountNumber());
            return true;
        }
        else{
            return false;
        }
    }
}
