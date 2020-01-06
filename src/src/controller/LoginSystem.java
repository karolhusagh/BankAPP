package controller;

import model.SQLquery;
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
        return Objects.equals(SQLquery.getPassword(login), getMd5(passwd));
    }




    public static void main(String[] args) {
        String s = "abc";
        System.out.println(checkPasswd("test1", "abc"));
    }
}
