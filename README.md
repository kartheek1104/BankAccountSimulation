# Bank Account Simulation

A simple Java console application that simulates basic bank operations such as:

- User registration with account number and PIN
- Login authentication
- Deposit and Withdraw money
- View current balance
- View transaction history
- View account summary

## Features

- Stores account data persistently in `accounts.txt`
- Stores transaction history in `<accountNumber>_history.txt`
- Basic input validation (e.g., positive amounts)
- File-based persistence without databases

## How to Compile and Run

```bash
javac BankApp.java
java BankApp
```

# Usage Example
```bash

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
```
