package view;

import controller.SQLquery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShowHistory extends JFrame {
    public JTable tableHistory;

    public ShowHistory(int num){
        setSize(500, 300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("Show history: Account "+num);
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        JTable tableHistory = new JTable();
        Object columns[] = {"Acc Nr.","Nr.", "Transaction Type", "Value", "Transaction Date"};
        defaultTableModel.setColumnIdentifiers(columns);

        tableHistory.setModel(SQLquery.ShowHistory(defaultTableModel,num));
        tableHistory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableHistory.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableHistory.getColumnModel().getColumn(1).setPreferredWidth(50);
        tableHistory.getColumnModel().getColumn(2).setPreferredWidth(160);
        tableHistory.getColumnModel().getColumn(3).setPreferredWidth(60);
        tableHistory.getColumnModel().getColumn(4).setPreferredWidth(160);


        // Import ImageIcon
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo,JLabel.CENTER);
        add(new JScrollPane(tableHistory));

        // Panels
        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.WHITE);
        panelTop.add(logo);

        add(BorderLayout.NORTH, panelTop);



        setVisible(true);

    }

    public static void main(String[] args) {
        new ShowHistory(1);
    }

}
