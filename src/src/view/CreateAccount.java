package view;

import controller.CreateClient;
import model.Client;
import model.SQLquery;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;



public class CreateAccount extends JFrame {
    private JTextField inputFirstName;
    private JTextField inputLastName;
    private JTextField inputid;
    private JTextField inputPassword;

    //TESTING

    public static void main(String[] args) {
        new CreateAccount();
    }

    CreateAccount() {

        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Create Account");

        //LABELS
        JLabel labelFirstName = new JLabel("First Name: ");
        JLabel labelLastName = new JLabel("Last Name: ");
        JLabel labelSelection = new JLabel("Please make a selection below");
        JLabel labelid = new JLabel("ID: ");
        JLabel labelPassword = new JLabel("Password: ");
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo,JLabel.CENTER);

        //INPUTS
        inputFirstName = new JTextField(15);
        inputLastName = new JTextField(15);
        inputid = new JTextField(15);
        inputPassword = new JTextField(15);


        //PANELS
        JPanel panelButtons = new JPanel();
        JPanel panelInputs = new JPanel();
        JPanel panelLabels = new JPanel();
        JPanel panelTop = new JPanel();


        //BUTTONS
        JButton buttonClear = new JButton("Close");
        buttonClear.addActionListener(e -> {
            dispose();
            new LoginAccount();
        });
        JButton buttonSubmit = new JButton("Submit");
        buttonSubmit.addActionListener(e -> checkClientInfo());

        panelLabels.setLayout(new GridLayout(4, 1));
        panelInputs.setLayout(new GridLayout(4, 1));


        panelLabels.add(labelFirstName);
        panelLabels.add(labelLastName);
        panelLabels.add(labelid);
        panelLabels.add(labelPassword);
        panelTop.add(logo);
        panelTop.add(labelSelection);


        panelInputs.add(inputFirstName);
        panelInputs.add(inputLastName);
        panelInputs.add(inputid);
        panelInputs.add(inputPassword);

        panelButtons.add(buttonSubmit);
        panelButtons.add(buttonClear);

        add(BorderLayout.NORTH, panelTop);
        add(BorderLayout.WEST, panelLabels);
        add(BorderLayout.EAST, panelInputs);
        add(BorderLayout.SOUTH, panelButtons);

        setVisible(true);


    }

    private void checkClientInfo() {
        BigDecimal balance = BigDecimal.valueOf(0);
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String id = inputid.getText();
        String password = inputPassword.getText();

        id = id.replaceAll("[- ]", "");


        if (firstName.length() == 0) {
            JOptionPane.showMessageDialog(null, "First name invalid");
            System.out.println("first name length: " + firstName.length());
        } else if (lastName.length() == 0) {
            JOptionPane.showMessageDialog(null, "Last name invalid");
            System.out.println("last name length: " + lastName.length());
        } else if (id.trim().length() != 9) {
            JOptionPane.showMessageDialog(null, "ID length must be 9");
        } else if (password.length() == 0) {
            JOptionPane.showMessageDialog(null, "Password invalid");
            System.out.println("Password name length: " + password.length());

        } else {

            CreateClient createClient = new CreateClient();
            createClient.AddClient(firstName, lastName, id, password, balance);


        }
    }

}


