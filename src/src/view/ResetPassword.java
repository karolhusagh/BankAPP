package view;

import javax.swing.*;
import java.awt.*;

public class ResetPassword extends JFrame {
    private JTextField inputid;
    private JPasswordField inputpasswdold;
    private JPasswordField inputpasswdnew;

    ResetPassword(){
        setSize(500, 300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Reset Password");

        // Import ImageIcon
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo,JLabel.CENTER);

        // Labels
        JLabel labelid =  new JLabel("Enter ID: ");
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
 /*       buttonConfirm.addActionListener(
                if(inputpasswdold != inputpasswdnew)
                    SQLresetpasswd(inputid,inputpasswdold,inputpasswdnew);

        ));
*/
        panelButtons.add(buttonClose);
        buttonClose.addActionListener(e -> dispose());


        panelEAST.setLayout(new GridLayout(3,1));
        panelWEST.setLayout(new GridLayout(3,1));
        add(BorderLayout.NORTH, panelTop);
        add(BorderLayout.CENTER, panelEAST);
        add(BorderLayout.WEST, panelWEST);
        add(BorderLayout.SOUTH, panelButtons);

        setVisible(true);

    }

    public static void main(String[] args) {
        new ResetPassword();
    }
}
