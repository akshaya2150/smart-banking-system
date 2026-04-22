package com.bank.banking_system.controller;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;
import java.util.*;

import com.bank.banking_system.model.User;
import com.bank.banking_system.model.Account;
import com.bank.banking_system.model.Transaction;
import com.bank.banking_system.repository.UserRepository;
import com.bank.banking_system.repository.AccountRepository;
import com.bank.banking_system.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ✅ HOME
    @GetMapping("/")
    public String home() {
        return "User API Working 🚀";
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "User Registered Successfully ✅";
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent() &&
                existingUser.get().getPassword().equals(user.getPassword())) {
            return "Login Successful ✅";
        }

        return "Invalid Credentials ❌";
    }

    // ✅ CREATE ACCOUNT
    @PostMapping("/createAccount")
    public String createAccount(@RequestBody Account account){

        String username = account.getUsername();
        String accountType = account.getAccountType();
        String pin = account.getPin();

        // 🔥 LIMIT CHECK (PUT HERE)
        long count = accountRepository.countByUsernameAndAccountType(username, accountType);

        // 🔥 PIN VALIDATION
        if(pin == null || !pin.matches("\\d{4}")){
            return "❌ PIN must be exactly 4 digits (numbers only)";
        }

        if(accountType.equals("Savings") && count >= 2){
            return "❌ Savings account limit reached (Max 2)";
        }

        if(accountType.equals("Current") && count >= 2){
            return "❌ Current account limit reached (Max 2)";
        }

        if(accountType.equals("Student") && count >= 1){
            return "❌ Student account limit reached (Max 1)";
        }

        // ✅ GENERATE ACCOUNT NUMBER
        account.setAccountNumber("ACC" + System.currentTimeMillis());

        // ✅ DEFAULT BALANCE
        account.setBalance(0);

        // ✅ SAVE
        accountRepository.save(account);

        return "✅ Account created successfully";
    }

    // ✅ DEPOSIT
    @PostMapping("/deposit")
    public String deposit(@RequestParam String accountNumber,
                          @RequestParam double amount){

        Account acc = accountRepository.findByAccountNumber(accountNumber).orElse(null);

        if(acc == null) return "Account not found ❌";

        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        // Transaction
        Transaction t = new Transaction();
        t.setType("Deposit");
        t.setAmount(amount);
        t.setDate(LocalDateTime.now());
        t.setAccountNumber(acc.getAccountNumber());

        transactionRepository.save(t);

        return "Deposit Successful ✅";
    }

    // ✅ WITHDRAW
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber,
                           @RequestParam double amount){

        Account acc = accountRepository.findByAccountNumber(accountNumber).orElse(null);

        if(acc == null) return "Account not found ❌";

        if(acc.getBalance() < amount){
            return "Insufficient Balance ❌";
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        // Transaction
        Transaction t = new Transaction();
        t.setType("Withdraw");
        t.setAmount(amount);
        t.setDate(LocalDateTime.now());
        t.setAccountNumber(acc.getAccountNumber());

        transactionRepository.save(t);

        return "Withdraw Successful ✅";
    }

    // ✅ TRANSFER
    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccount,
                           @RequestParam String toAccount,
                           @RequestParam double amount){

        Account sender = accountRepository.findByAccountNumber(fromAccount).orElse(null);
        Account receiver = accountRepository.findByAccountNumber(toAccount).orElse(null);

        if(sender == null || receiver == null){
            return "Account not found ❌";
        }

        if(sender.getBalance() < amount){
            return "Insufficient Balance ❌";
        }

        // Update balances
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Sender Transaction
        Transaction t1 = new Transaction();
        t1.setType("Transfer Sent");
        t1.setAmount(amount);
        t1.setDate(LocalDateTime.now());
        t1.setAccountNumber(sender.getAccountNumber());
        transactionRepository.save(t1);

        // Receiver Transaction
        Transaction t2 = new Transaction();
        t2.setType("Transfer Received");
        t2.setAmount(amount);
        t2.setDate(LocalDateTime.now());
        t2.setAccountNumber(receiver.getAccountNumber());
        transactionRepository.save(t2);

        return "Transfer Successful ✅";
    }

    // ✅ TRANSACTION HISTORY (FIXED 🔥)
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam String accountNumber){
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    // ✅ CHECK BALANCE (PIN BASED 🔐)
    @GetMapping("/balance")
    public double getBalance(@RequestParam String accountNumber,
                             @RequestParam String pin){

        Account acc = accountRepository.findByAccountNumber(accountNumber).orElse(null);

        if(acc == null || !acc.getPin().equals(pin)){
            return -1;
        }

        return acc.getBalance();
    }

    // ✅ CHECK IF USER HAS ACCOUNT
    @GetMapping("/hasAccount")
    public boolean hasAccount(@RequestParam String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    // ✅ GET ALL USER ACCOUNTS
    @GetMapping("/accounts")
    public List<Account> getUserAccounts(@RequestParam String username){
        return accountRepository.findAllByUsername(username);
    }

    @GetMapping("/accountSummary")
    public Map<String, Object> accountSummary(@RequestParam String username){

        List<Account> accounts = accountRepository.findAllByUsername(username);

        Map<String, Object> res = new HashMap<>();

        res.put("totalAccounts", accounts.size());

        Map<String, Long> typeCount = new HashMap<>();

        typeCount.put("Savings", accountRepository.countByUsernameAndAccountType(username,"Savings"));
        typeCount.put("Current", accountRepository.countByUsernameAndAccountType(username,"Current"));
        typeCount.put("Student", accountRepository.countByUsernameAndAccountType(username,"Student"));

        res.put("typeCount", typeCount);

        return res;
    }
}