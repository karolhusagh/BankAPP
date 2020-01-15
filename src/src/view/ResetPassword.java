package view;

import controller.LoginSystem;
import model.Client;

import javax.swing.*;
import java.awt.*;

class ResetPassword extends JFrame {
    private JTextField inputid;
    private JPasswordField inputpasswdold;
    private JPasswordField inputpasswdnew;

    ResetPassword(Client client) {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Reset Password");

        // Import ImageIcon
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo, JLabel.CENTER);

        // Labels
        JLabel labelid = new JLabel("Enter ID: ");
        JLabel labelpasswdold = new JLabel("Enter password: ");
        JLabel labelpasswdnew = new JLabel("Enter new password: ");

        // input
        inputid = new JTextField(1);
        inputpasswdold = new JPasswordField(1);
        inputpasswdnew = new JPasswordField(1);

        //buttons
        JButton buttonConfirm = new JButton("Confirm");
        JButton buttonClose = new JButton("Close");

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
        panelWEST.add(labelid);
        panelWEST.add(labelpasswdold);
        panelWEST.add(labelpasswdnew);
        panelEAST.add(inputid);
        panelEAST.add((inputpasswdold));
        panelEAST.add(inputpasswdnew);

        panelButtons.add(buttonConfirm);
        buttonConfirm.addActionListener(e -> {
            change(client);
            JOptionPane.showMessageDialog(null,"Password reset successfully");
            dispose();
        });

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

    private void change(Client client) {
        if (LoginSystem.getMd5(String.copyValueOf(inputpasswdold.getPassword())).equals(client.getPassword())) {
            client.changePassword(LoginSystem.getMd5(String.copyValueOf(inputpasswdnew.getPassword())));
        } else {
            JOptionPane.showMessageDialog(null, "Wrong password");
        }
    }
}
