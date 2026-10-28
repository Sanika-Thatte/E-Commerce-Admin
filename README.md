# E-Commerce-Admin
This is a E-Commerce Application Admin panel. Here you can add, delete, edit products with its proper name, category, price of  product.

# 🛒 E-Commerce Admin & Backend Platform

[![Spring Boot](https://shields.io)](https://spring.io)
[![Java](https://shields.io)](https://oracle.com)
[![MySQL](https://shields.io)](https://mysql.com)

A secure, robust Spring Boot REST API backend application serving as the core engine for an **E-Commerce Platform**. This system provides a unified backend architecture capable of handling product management, persistent user shopping carts, and checkout order processing.

---

## 🌟 Core System Features

This platform manages the foundational lifecycle of an online marketplace, split into three major modules:

### 📦 1. Product Management (`Add / Update / Delete`)
* **Inventory Entry:** Allows administrators to securely input new products with titles, deep descriptions, pricing tiers, and stock tallies.
* **Media Uploads Engine:** Integrated multi-part request system handling image file streaming (`spring.servlet.multipart.enabled=true`) to upload and bind product pictures directly.
* **Dynamic Restocking:** Tracks and updates database product layers automatically as items sell out.

### 🛒 2. Shopping Cart Operations
* **Persistent Sessions:** Saves user cart selections reliably within the database schema.
* **Flexible Cart Actions:** Provides endpoints to append items, scale quantities up or down, or erase items entirely from the active checkout list.
* **Price Computations:** Calculates subtotal pricing configurations on-the-fly reflecting real-time stock pricing adjustments.

### 📋 3. Order Processing & Checkout
* **Cart Transition:** Locks cart parameters and securely transitions active user carts into finalized, unalterable order items.
* **Order History Logs:** Generates persistent receipt histories mapping unique buyer IDs against order records, total payouts, and timestamps.

---

## 🛠️ Tech Stack & Architecture

* **Framework:** Spring Boot (v3.x)
* **Language:** Java 17+
* **Database:** MySQL
* **ORM / Data Access:** Spring Data JPA / Hibernate
* **Build Tool:** Maven (Embedded via Maven Wrapper)
* **Server Port:** `8081`

---

## 📁 System Requirements & Dependencies

Ensure you have the following installed on your local development machine:
1. **Java JDK 17** or higher (configured in your system environment path).
2. **MySQL Community Server** running locally.
3. An IDE of your choice (IntelliJ IDEA, Eclipse/STS, or VS Code).

---

## ⚙️ Step-by-Step Local Setup Instructions

### 1. Initialize the Local Database
Log into your MySQL Workbench or Command Line Interface and execute the database initialization query:
```sql
CREATE DATABASE commerce;
```
*Note: You do not need to create tables manually. Hibernate's DDL auto-configuration (`spring.jpa.hibernate.ddl-auto=update`) will build the product, cart, and order tables dynamically upon application boot.*

### 2. Configure Your Database Password (Security System)
To prevent tracking sensitive security credentials, this project isolates database passwords using environment variables. **Do not modify the `application.properties` file directly.**

Choose the command matching your local terminal platform to bind your database password:

#### 💻 Windows Command Prompt (cmd)
```cmd
set DB_PASSWORD=your_actual_mysql_password
```

#### 🟦 Windows PowerShell
```powershell
\$env:DB_PASSWORD="your_actual_mysql_password"
```

#### 🍎 macOS & 🐧 Linux Terminal
```bash
export DB_PASSWORD="your_actual_mysql_password"
```

---

## 🚀 Compiling and Running the Application

You do not need to install Maven globally on your computer. The repository packages an embedded Maven wrapper file sequence (`mvnw`).

Open your terminal in the root directory of the project and execute:

* **Windows Machines:**
  ```cmd
  mvnw.cmd spring-boot:run
  ```
* **macOS / Linux Machines:**
  ```bash
  chmod +x mvnw
  ./mvnw spring-boot:run
  ```

Once your console outputs the initialization confirmation string `Started CommerceApplication in X.XXX seconds`, your application is live.

🔗 **Base Local Endpoint:** `http://localhost:8081`

---

## 💾 Core Properties Overview

The internal configurations bound inside `src/main/resources/application.properties` manage the following behaviors:

| Property Key | Default Configuration Value | Operational Purpose |
| :--- | :--- | :--- |
| `server.port` | `8081` | Binds the active web server port listener |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/commerce` | Establishes the driver connection path |
| `spring.datasource.username` | `root` | System database profile handle |
| `spring.datasource.password` | `${DB_PASSWORD:}` | Secure environmental abstraction parameter |
| `spring.jpa.hibernate.ddl-auto`| `update` | Automatically patches SQL data structures |
| `spring.servlet.multipart.enabled`| `true` | Allows image/file data streaming extensions |

---

## 🔧 Troubleshooting Guide

* **Error: `Access denied for user 'root'@'localhost'`**
  * **Fix:** The application cannot detect your password. Ensure you explicitly set the `DB_PASSWORD` variable in the exact same terminal instance you are running `mvnw`.
* **Error: `Unknown database 'commerce'`**
  * **Fix:** Verify you have executed the `CREATE DATABASE commerce;` script in your database system.
* **Error: `Port 8081 was already in use`**
  * **Fix:** Another process is blocking port `8081`. You can free up the port or change `server.port=8081` to another value inside `application.properties` (e.g., `8082`).


