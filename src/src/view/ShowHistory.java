package view;

import model.SQLquery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class ShowHistory extends JFrame {
    ShowHistory(int num) {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("Show history: Account " + num);
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        JTable tableHistory = new JTable();
        Object[] columns = {"Acc Nr.", "Transaction Type", "Value", "Transaction Date"};
        defaultTableModel.setColumnIdentifiers(columns);

        tableHistory.setModel(SQLquery.ShowHistory(defaultTableModel, num));
        tableHistory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableHistory.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableHistory.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableHistory.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableHistory.getColumnModel().getColumn(3).setPreferredWidth(150);

        // Import ImageIcon
        ImageIcon IMGlogo = new ImageIcon("img\\Bankimg.png");
        JLabel logo = new JLabel(IMGlogo, JLabel.CENTER);
        add(new JScrollPane(tableHistory));

        // Panels
        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.WHITE);
        panelTop.add(logo);

        add(BorderLayout.NORTH, panelTop);
        setVisible(true);
    }
}
