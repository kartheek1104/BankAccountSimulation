package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import model.Account;
import util.FileUtil;
import util.HashUtil;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField accNoField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Account No:"));
        add(accNoField);
        add(new JLabel("PIN:"));
        add(pinField);
        add(registerBtn);
        add(backBtn);

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String accNo = accNoField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            if (name.isEmpty() || accNo.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required");
                return;
            }

            Map<String, Account> accounts = FileUtil.loadAccounts();

            if (accounts.containsKey(accNo)) {
                JOptionPane.showMessageDialog(this, "Account number already exists");
                return;
            }

            String hashedPin = HashUtil.hash(pin);
            Account acc = new Account(name, accNo, hashedPin);
            accounts.put(accNo, acc);
            FileUtil.saveAccounts(accounts);
            JOptionPane.showMessageDialog(this, "Registration successful");
            new LoginFrame().setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }
}
