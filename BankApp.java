import java.util.*;
import java.io.*;

class Transaction {
    private String type;
    private double amount;
    private Date timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + type.toUpperCase() + ": " + amount + " rupees";
    }
}

class Account {
    private String name;
    private String accNo;
    private String pin;
    private double balance;
    private List<Transaction> transactions;

    public Account(String name, String accNo, String pin, double balance) {
        this.name = name;
        this.accNo = accNo;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccNo() {
        return accNo;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws IOException {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        balance += amount;
        Transaction t = new Transaction("Deposit", amount);
        transactions.add(t);
        save();
        saveTransaction(t);
        System.out.println("Deposited " + amount + " rupees");
    }

    public void withdraw(double amount) throws IOException {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        Transaction t = new Transaction("Withdraw", amount);
        transactions.add(t);
        save();
        saveTransaction(t);
        System.out.println("Withdrew " + amount + " rupees");
    }

    public void printHistory() throws IOException {
        File file = new File(accNo + "_history.txt");
        if (!file.exists() || file.length() == 0) {
            System.out.println("No transactions yet.");
            return;
        }
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
        }
        reader.close();
    }

    public void summary() {
        System.out.println("Account Holder: " + name);
        System.out.println("Account Number: " + accNo);
        System.out.println("Current Balance: " + balance + " rupees");
    }

    private void save() throws IOException {
        File inputFile = new File("accounts.txt");
        File tempFile = new File("accounts_temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        boolean found = false;

        while ((currentLine = reader.readLine()) != null) {
            String[] parts = currentLine.split(",");
            if (parts[1].equals(accNo)) {
                writer.write(name + "," + accNo + "," + pin + "," + balance + "\n");
                found = true;
            } else {
                writer.write(currentLine + "\n");
            }
        }

        if (!found) {
            writer.write(name + "," + accNo + "," + pin + "," + balance + "\n");
        }

        writer.close();
        reader.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    private void saveTransaction(Transaction t) throws IOException {
        FileWriter fw = new FileWriter(accNo + "_history.txt", true);
        fw.write(t.toString() + "\n");
        fw.close();
    }

    public static Account login(String accNo, String pin) throws IOException {
        File file = new File("accounts.txt");
        if (!file.exists()) return null;

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(",");
            if (parts[1].equals(accNo) && parts[2].equals(pin)) {
                sc.close();
                return new Account(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
            }
        }
        sc.close();
        return null;
    }

    public static boolean accountExists(String accNo) throws IOException {
        File file = new File("accounts.txt");
        if (!file.exists()) return false;

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(",");
            if (parts[1].equals(accNo)) {
                sc.close();
                return true;
            }
        }
        sc.close();
        return false;
    }
}

public class BankApp {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Account current = null;

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                System.out.print("Enter Account Number: ");
                String acc = sc.nextLine();
                System.out.print("Enter PIN: ");
                String pin = sc.nextLine();

                current = Account.login(acc, pin);
                if (current == null) {
                    System.out.println("Invalid credentials.");
                } else {
                    System.out.println("Login successful.");
                    break;
                }
            } else if (ch == 2) {
                System.out.print("Enter Name: ");
                String name = sc.nextLine();
                System.out.print("Enter Account Number: ");
                String acc = sc.nextLine();
                if (Account.accountExists(acc)) {
                    System.out.println("Account number already exists.");
                    continue;
                }
                System.out.print("Set PIN: ");
                String pin = sc.nextLine();

                FileWriter fw = new FileWriter("accounts.txt", true);
                fw.write(name + "," + acc + "," + pin + "," + 0.0 + "\n");
                fw.close();

                System.out.println("Registration complete.");
            } else if (ch == 0) {
                System.exit(0);
            }
        }

        while (true) {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Summary");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Amount: ");
                    current.deposit(sc.nextDouble());
                    break;
                case 2:
                    System.out.print("Amount: ");
                    current.withdraw(sc.nextDouble());
                    break;
                case 3:
                    System.out.println("Balance: " + current.getBalance() + " rupees");
                    break;
                case 4:
                    current.printHistory();
                    break;
                case 5:
                    current.summary();
                    break;
                case 0:
                    System.out.println("Logged out.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}