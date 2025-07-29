package gui;

import model.Account;
import util.FileUtil;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DashboardFrame extends JFrame {
    private Account acc;
    private JLabel balanceLabel;

    public DashboardFrame(Account acc) {
        this.acc = acc;

        setTitle("Dashboard - " + acc.getName());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        balanceLabel = new JLabel("Current Balance: " + acc.getBalance() + " rupees");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton historyBtn = new JButton("Transaction History");
        JButton logoutBtn = new JButton("Logout");
        JButton resetBtn = new JButton("Reset Account");

        add(balanceLabel);
        add(depositBtn);
        add(withdrawBtn);
        add(historyBtn);
        add(resetBtn);
        add(logoutBtn);

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount <= 0) throw new Exception();
                    acc.deposit(amount);
                    updateBalance("Deposited " + amount + " rupees");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount");
                }
            }
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (acc.withdraw(amount)) {
                        updateBalance("Withdrew " + amount + " rupees");
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient balance");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount");
                }
            }
        });

        historyBtn.addActionListener(e -> {
            java.util.List<String> history = FileUtil.loadHistory(acc.getAccNo());
            String msg = history.isEmpty() ? "No transactions yet" : String.join("\n", history);
            JOptionPane.showMessageDialog(this, msg);
        });

        resetBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Reset account?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                acc.withdraw(acc.getBalance()); // bring balance to zero
                FileUtil.saveHistory(acc.getAccNo(), new ArrayList<>());
                updateBalance("Account reset");
            }
        });

        logoutBtn.addActionListener(e -> {
            // Save updated account on logout
            Map<String, Account> accounts = FileUtil.loadAccounts();
            accounts.put(acc.getAccNo(), acc);
            FileUtil.saveAccounts(accounts);

            new LoginFrame().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    private void updateBalance(String message) {
        // Save updated account info to file
        Map<String, Account> accounts = FileUtil.loadAccounts();
        accounts.put(acc.getAccNo(), acc);
        FileUtil.saveAccounts(accounts);

        // Update transaction history
        java.util.List<String> history = FileUtil.loadHistory(acc.getAccNo());
        history.add(new Date() + " - " + message);
        FileUtil.saveHistory(acc.getAccNo(), history);

        balanceLabel.setText("Current Balance: " + acc.getBalance() + " rupees");
        JOptionPane.showMessageDialog(this, message);
    }
}
