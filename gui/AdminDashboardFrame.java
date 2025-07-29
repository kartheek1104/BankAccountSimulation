package gui;

import model.Account;
import util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class AdminDashboardFrame extends JFrame {

    private JTable accountsTable;
    private DefaultTableModel model;

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard - Manage Accounts");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        accountsTable = new JTable();
        loadAccountsIntoTable();

        JScrollPane scrollPane = new JScrollPane(accountsTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadAccountsIntoTable());

        JButton deleteButton = new JButton("Delete Selected Account");
        deleteButton.addActionListener(e -> deleteSelectedAccount());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadAccountsIntoTable() {
        Map<String, Account> accounts = FileUtil.loadAccounts();

        String[] columns = {"Account Number", "Name", "Balance"};
        model = new DefaultTableModel(columns, 0);

        for (Account account : accounts.values()) {
            Object[] row = {
                    account.getAccNo(),
                    account.getName(),
                    account.getBalance()
            };
            model.addRow(row);
        }
        accountsTable.setModel(model);
    }

    private void deleteSelectedAccount() {
        int selectedRow = accountsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String accNo = (String) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete account: " + accNo + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Map<String, Account> accounts = FileUtil.loadAccounts();

            if (accounts.containsKey(accNo)) {
                accounts.remove(accNo);
                FileUtil.saveAccounts(accounts);

                // Delete transaction history file
                File historyFile = new File("data/" + accNo + "_history.json");
                if (historyFile.exists()) {
                    if (historyFile.delete()) {
                        System.out.println("Transaction history file deleted for account: " + accNo);
                    } else {
                        System.err.println("Failed to delete transaction history for account: " + accNo);
                    }
                }

                loadAccountsIntoTable();

                JOptionPane.showMessageDialog(this, "Account " + accNo + " and related data deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
