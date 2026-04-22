# 💳 Smart Banking System

A full-stack banking simulation system built using **Java (Spring Boot)** and **HTML, CSS, JavaScript**.

---

## 🚀 Features

### 👤 User Management

* User Registration & Login
* Authentication system

### 🏦 Account Management

* Create multiple accounts (Savings, Current, Student)
* Account limits:

  * Savings → 2
  * Current → 2
  * Student → 1
* Unique Account Number generation

### 💰 Transactions

* Deposit money
* Withdraw money (with balance validation)
* Transfer money (Account → Account)
* Real-time balance updates

### 🔐 Security

* PIN-based balance access
* Hidden balance until correct PIN entered

### 📊 Dashboard

* Multi-account selection (card UI)
* Transaction history
* Last transaction preview
* Search transactions
* Dark/Light mode

---

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA
* MySQL

### Frontend

* HTML
* CSS (Glassmorphism UI)
* JavaScript (Fetch API)

---

## 📸 Screenshots

*Add screenshots of your UI here*

---

## ⚙️ Setup Instructions

### 1. Clone repository

```bash
git clone https://github.com/YOUR_USERNAME/smart-banking-system.git
```

### 2. Backend Setup

* Open in IntelliJ / Eclipse
* Configure MySQL in `application.properties`
* Run Spring Boot application

### 3. Frontend Setup

* Open `index.html` in browser
* Ensure backend runs on:

```
http://localhost:8081
```

---

## 📌 API Endpoints

* `/users/register`
* `/users/login`
* `/users/createAccount`
* `/users/deposit`
* `/users/withdraw`
* `/users/transfer`
* `/users/accounts`
* `/users/transactions`
* `/users/balance`

---

## 🎯 Project Highlights

* Real-world banking simulation
* Multi-account system
* Secure PIN verification
* Clean UI with animations
* Full CRUD operations

---

## 🚀 Future Enhancements

* 📊 Spending analytics chart
* 🔔 Notifications system
* 📄 PDF bank statement download
* 👤 Profile management

---

## 👩‍💻 Author

Deepika

---

⭐ If you like this project, give it a star!
