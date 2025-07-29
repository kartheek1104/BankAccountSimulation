package model;

public class Account {
    private String name;
    private String accNo;
    private String pinHash;
    private double balance;

    public Account(String name, String accNo, String pinHash) {
        this.name = name;
        this.accNo = accNo;
        this.pinHash = pinHash;
        this.balance = 0.0;
    }

    public String getName() {
        return name;
    }

    public String getAccNo() {
        return accNo;
    }

    public String getPinHash() {
        return pinHash;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
