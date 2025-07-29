# Bank Account Simulation (Java Swing Application)

This is a Java Swing-based desktop application that simulates a simple banking system. It supports both user and admin functionality with a graphical interface for managing accounts. Data is stored persistently using file-based storage with JSON, and PINs are encrypted using SHA-256.


## Features

### User Features

- Register a new bank account with name, account number, and PIN
- Secure login using account number and PIN
- Deposit and withdraw funds
- View current account balance
- View transaction history
- View complete account summary

### Admin Features

- Secure admin login (credentials defined in `AdminConstants.java`)
- View all registered user accounts
- Delete (terminate) selected user accounts
- View usernames and account numbers of all users

### Security

- All PINs are encrypted using SHA-256 hashing via `HashUtil.java`
- No plain-text PINs are stored

### Data Persistence

- All account data is stored in `accounts.txt` as JSON
- Transaction histories are stored in individual files: `<accountNumber>_history.txt`


## Compilation and Execution Instructions

1. Open the terminal and navigate to the project directory.

2. Compile the project:

```bash
javac -cp ".;lib/gson-2.10.1.jar" gui/*.java model/*.java util/*.java Main.java
```

_Note: Use `:` instead of `;` on macOS/Linux._

3. Run the application:

```bash
javac BankApp.java
java BankApp


# Usage Example

1. Login
2. Register
0. Exit
Choice: 2
Enter Name: John Doe
Enter Account Number: 1234567890
Set PIN: 1234
Registration complete.

1. Login
2. Register
0. Exit
Choice: 1
Enter Account Number: 1234567890
Enter PIN: 1234
Login successful.

1. Deposit
2. Withdraw
3. Balance
4. Transaction History
5. Summary
0. Logout
Choice: 1
Amount: 50000
Deposited 50000 rupees

Choice: 2
Amount: 25000
Withdrew 25000 rupees

Choice: 5
Account Holder: John Doe
Account Number: 1234567890
Current Balance: 25000 rupees