package view;

import javax.swing.*;

import java.awt.*;

import static controller.LoginSystem.checkPasswd;

public class LoginAccount extends JFrame {
    private JTextField inputid;
    private JPasswordField inputpasswd;

    public static void main(String[] args) {
        new LoginAccount();
    }
    LoginAccount() {
        setSize(500, 300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Log in to account");

        // Import ImageIcon
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo,JLabel.CENTER);


        //labels
        JLabel labelaccnum = new JLabel("ID number: ");
        JLabel labelpasswd = new JLabel("Password: ");

        //input
        inputid = new JTextField(15);
        inputpasswd = new JPasswordField(15);

        //PANELS
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(Color.WHITE);
        JPanel panelInputs = new JPanel();
        panelInputs.setBackground(Color.WHITE);
        JPanel panelLabels = new JPanel();
        panelLabels.setBackground(Color.WHITE);
        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.WHITE);

        //BUTTONS
        JButton buttonLogin = new JButton("Login");
        JButton buttonCreate = new JButton("Create");

        buttonLogin.addActionListener(e -> checkPasswd(inputid.getText(),String.copyValueOf(inputpasswd.getPassword())));
        buttonCreate.addActionListener(e -> {
                    dispose();
                    new CreateAccount();});

        Dimension textFieldDimension = new Dimension(15, 15);

        inputid.setPreferredSize(textFieldDimension);
        inputpasswd.setPreferredSize(textFieldDimension);

        panelInputs.add(inputid);
        panelInputs.add(inputpasswd);

        panelLabels.add(labelaccnum);
        panelLabels.add(labelpasswd);

        panelButtons.add(buttonLogin);
        panelButtons.add(buttonCreate);
        panelTop.add(logo);
        panelLabels.setLayout(new GridLayout(2,1));
        panelInputs.setLayout(new GridLayout(2,1));


        add(BorderLayout.NORTH, panelTop);
        add(BorderLayout.WEST, panelLabels);
        add(BorderLayout.CENTER, panelInputs);
        add(BorderLayout.SOUTH, panelButtons);

        setVisible(true);

    }

}
