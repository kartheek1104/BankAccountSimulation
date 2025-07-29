package gui;

import model.Account;
import util.FileUtil;
import util.AdminConstants;
import util.HashUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LoginFrame extends JFrame {

    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JButton loginButton, registerButton, adminLoginButton;

    public LoginFrame() {
        setTitle("Bank Account Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 180);
        setLocationRelativeTo(null);

        // Input panel for account no and pin
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        inputPanel.add(accountNumberField);
        inputPanel.add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        inputPanel.add(pinField);
        add(inputPanel, BorderLayout.CENTER);

        // Button panel with 3 buttons side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        loginButton = new JButton("User Login");
        registerButton = new JButton("Register");
        adminLoginButton = new JButton("Admin Login");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminLoginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // User login action
        loginButton.addActionListener(e -> {
            String accNo = accountNumberField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            Map<String, Account> accounts = FileUtil.loadAccounts();
            Account account = accounts.get(accNo);

            if (account != null && account.getPinHash().equals(HashUtil.hash(pin))) {
                new DashboardFrame(account).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Open registration frame
        registerButton.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        // Show admin login dialog on admin button click
        adminLoginButton.addActionListener(e -> showAdminLoginDialog());
    }

    private void showAdminLoginDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        panel.add(new JLabel("Admin Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Admin PIN:"));
        panel.add(pinField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Admin Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            if (username.equals(AdminConstants.ADMIN_USERNAME) &&
                    HashUtil.hash(pin).equals(AdminConstants.ADMIN_PIN_HASH)) {
                new AdminDashboardFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
