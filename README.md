# 🛒 Complete E-Commerce Platform Architecture

[![Spring Boot](https://shields.io)](https://spring.io)
[![Java](https://shields.io)](https://oracle.com)
[![MySQL](https://shields.io)](https://mysql.com)

A comprehensive, split-architecture E-Commerce platform built using Spring Boot and MySQL. The ecosystem is divided into two distinct, decoupled micro-applications operating on different local ports to isolate administrative workflows from public customer traffic.

---

## 🏗️ Repository Architecture

This project is organized as a monorepository containing both application subsystems:

```text
my-ecommerce-platform/
│
├── 📁 ecommerce-customer/   (Runs on Port 8080)
│     ├── 📁 src/             (Customer marketplace logic)
│     └── 📄 pom.xml
│
└── 📁 ecommerce-admin/       (Runs on Port 8081)
      ├── 📁 src/             (Admin management logic)
      └── 📄 pom.xml
```

---

## 🌟 Application Features Matrix

### 👤 1. Customer Marketplace Platform (`Port 8080`)
The client-facing subsystem provides a smooth shopping experience for public visitors:
* **Product Discovery:** Browse available store inventories and view attached product images.
* **Smart Search:** Search and find items effortlessly from the product catalog.
* **Persistent Shopping Cart:** Add items to a shopping cart, modify ordered item quantities dynamically, or remove items before checking out.
* **Checkout & Orders:** Finalize checkout workflows to transition shopping carts into locked customer order logs with individual transaction tracking records.

### 👑 2. Admin Management Platform (`Port 8081`)
The administrative subsystem empowers shop owners to manage the store's core inventory and catalog layers:
* **Add Products:** Initialize new items with structural attributes (Title, detailed Descriptions, Price tiers, and stock tallies).
* **Media Uploads Engine:** Stream multi-part network payloads (`spring.servlet.multipart.enabled=true`) to upload and bind product imagery natively.
* **View & Audit:** Fetch real-time lists or singular item lookups directly from the unified database.
* **Edit & Refactor:** Dynamically update pricing details, adjust inventories, modify text descriptions, or swap image links.
* **Delete Catalog Entries:** Hard-remove outdated or discontinued items permanently from the system space.

---

## ⚙️ Step-by-Step Local Setup Instructions

### 1. Initialize the Unified Database
Both applications connect to the same central database. Log into your MySQL Workbench or CLI and run:
```sql
CREATE DATABASE commerce;
```
*Note: Hibernate's DDL auto-configuration (`spring.jpa.hibernate.ddl-auto=update`) in either app will build and patch the database schema dynamically upon boot.*

### 2. Configure Your Database Password (Security System)
To prevent tracking sensitive security credentials, both applications isolate database passwords using environment variables. **Do not write your password inside either `application.properties` file.**

Set your variable in the terminal instance before running the apps:

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

## 🚀 Launching the Ecosystem

Navigate into your preferred project directory and use the packaged Maven wrapper files (`mvnw`). You must run these in separate terminal windows:

### Launch the Customer App (`Port 8080`)
```cmd
cd ecommerce-customer
mvnw.cmd spring-boot:run
```
🔗 **Endpoint:** `http://localhost:8080`

### Launch the Admin Panel (`Port 8081`)
```cmd
cd ecommerce-admin
mvnw.cmd spring-boot:run
```
🔗 **Endpoint:** `http://localhost:8081`

---

## 💾 Core Properties Profile

Both services operate on matching database drivers but separate web listening targets:

| Property Configuration Key | Customer Subsystem (`8080`) | Admin Subsystem (`8081`) |
| :--- | :--- | :--- |
| `server.port` | `8080` | `8081` |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/commerce` | `jdbc:mysql://localhost:3306/commerce` |
| `spring.datasource.username` | `root` | `root` |
| `spring.datasource.password` | `${DB_PASSWORD:}` | `${DB_PASSWORD:}` |
| `spring.jpa.hibernate.ddl-auto`| `update` | `update` |
| `spring.servlet.multipart.enabled`| `true` | `true` |

