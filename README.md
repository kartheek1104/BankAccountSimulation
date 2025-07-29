# Bank Account Simulation (Java Swing Application)

This is a Java Swing-based desktop application that simulates a simple banking system. It supports both user and admin functionality with a graphical interface for managing accounts. Data is stored persistently using file-based storage with JSON, and PINs are encrypted using SHA-256.

---

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

---

## Project Structure

```
Bank Account Simulation/
│
├── gui/
│   ├── AdminDashboardFrame.java
│   ├── DashboardFrame.java
│   ├── LoginFrame.java
│   └── RegisterFrame.java
│
├── model/
│   └── Account.java
│
├── util/
│   ├── AdminConstants.java
│   ├── FileUtil.java
│   └── HashUtil.java
│
├── lib/
│   └── gson-2.10.1.jar
│
├── Main.java
└── accounts.txt (auto-generated at runtime)
```

---

## Compilation and Execution Instructions

1. Open the terminal and navigate to the project directory.

2. Compile the project:

```bash
javac -cp ".;lib/gson-2.10.1.jar" gui/*.java model/*.java util/*.java Main.java
```

_Note: Use `:` instead of `;` on macOS/Linux._

3. Run the application:

```bash
java -cp ".;lib/gson-2.10.1.jar" Main
```

---

## Admin Login Credentials

These are set in the `AdminConstants.java` file. You can change them if needed.

```java
public class AdminConstants {
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "enteryourpasswordhere";
}
```

---

## Example Usage

### Registering a User
- Enter name, unique account number, and a PIN
- The account will be saved in `accounts.txt` with PIN encrypted

### Logging In as User
- After entering valid credentials, users can:
  - Deposit/withdraw money
  - View balance and history
  - See a summary of the account

### Logging In as Admin
- After successful admin login, the admin can:
  - View all user accounts
  - Delete/terminate accounts

---

## Requirements

- Java 8 or later
- GSON library (`gson-2.10.1.jar` in `lib/` folder)

---

## Notes

- Accounts and transaction files are stored locally. Deleting an account removes the user's data from `accounts.txt`, but not their individual transaction history file.
- Data is not stored in a database. This app is file-driven for simplicity and educational purposes.


