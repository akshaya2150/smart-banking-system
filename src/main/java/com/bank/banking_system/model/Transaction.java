package com.bank.banking_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // optional (can keep or remove later)
    private double amount;
    private String type;
    private LocalDateTime date;
    private String accountNumber;

    // ✅ DEFAULT CONSTRUCTOR
    public Transaction() {}

    // ✅ OPTIONAL CONSTRUCTOR
    public Transaction(String username, double amount, String type) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.date = LocalDateTime.now();
    }

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}