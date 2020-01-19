package view;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import static java.math.RoundingMode.DOWN;

class TransferAccount extends JFrame {
    private JTextField inputidFrom;
    private JTextField inputidTo;
    private JTextField inputamount;

    TransferAccount(Client client) {
        setSize(500, 300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("New Transfer");

        // Import ImageIcon
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo, JLabel.CENTER);


        //labels
        JLabel labelidFrom = new JLabel("Your Account Number : ");
        JLabel labelidTo = new JLabel("To Account Number: ");
        JLabel labelamount = new JLabel("Enter amount: ");


        //input
        inputidFrom = new JTextField(1);
        inputidTo = new JTextField(1);
        inputamount = new JTextField(1);

        //buttons
        JButton buttonConfirm = new JButton("Confirm");
        JButton buttonClose = new JButton("Close");

        buttonConfirm.addActionListener(e -> {
            transaction(client);
        });

        //PANELS
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(Color.WHITE);
        JPanel panelWEST = new JPanel();
        panelWEST.setBackground(Color.WHITE);
        JPanel panelEAST = new JPanel();
        panelEAST.setBackground(Color.WHITE);
        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.WHITE);

        panelTop.add(logo);
        panelWEST.add(labelidFrom);
        panelWEST.add(labelidTo);
        panelWEST.add(labelamount);

        panelEAST.add(inputidFrom);
        panelEAST.add(inputidTo);
        panelEAST.add(inputamount);

        panelButtons.add(buttonConfirm);
        panelButtons.add(buttonClose);
        buttonClose.addActionListener(e -> dispose());

        panelEAST.setLayout(new GridLayout(3, 1));
        panelWEST.setLayout(new GridLayout(3, 1));
        add(BorderLayout.NORTH, panelTop);
        add(BorderLayout.CENTER, panelEAST);
        add(BorderLayout.WEST, panelWEST);
        add(BorderLayout.SOUTH, panelButtons);

        setVisible(true);

    }

    private void transaction(Client client) {
        try{
        if (Integer.valueOf(inputidFrom.getText()).equals(client.getAccountNumber()))
            client.transaction(Integer.parseInt(inputidTo.getText()), new BigDecimal(inputamount.getText().trim().replace(",", ".")).setScale(2, DOWN));
        else {
            JOptionPane.showMessageDialog(null, "Wrong account number!");
        }
        } catch(NumberFormatException NFE){
                JOptionPane.showMessageDialog(null,"Enter proper value!");
            }
    }
}
