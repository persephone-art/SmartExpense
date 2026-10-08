# 💰 SmartExpense — Smart Expense and Income Tax Assistant

A web-based personal finance management system built using **Java, Spring Boot, Thymeleaf, and MySQL**. SmartExpense helps users record income and expenses, monitor their financial balance, analyze monthly spending, and compare estimated income tax under two tax regimes.

## 📌 About the Project

SmartExpense is designed to make personal financial management easier through a simple, user-friendly website. Users can create accounts, log in, manage their financial records, and view reports based on their saved data.

This project was developed as a Java Object-Oriented Programming (OOP) project.

## ✨ Features

* 🔐 **User Registration and Login** — Create an account and sign in.
* 📊 **Personal Dashboard** — View income, expenses, and remaining balance.
* 💵 **Income Management** — Record income sources, amounts, dates, and descriptions.
* 🧾 **Expense Management** — Track spending by category, amount, date, and description.
* 📈 **Financial Reports** — View income and expense summaries, monthly analysis, and expense category totals.
* 🧮 **Tax Comparison** — Compare illustrative tax estimates under the old and new tax regimes.
* 👤 **User-Specific Records** — Retrieve financial data using the logged-in user's ID.
* 🚪 **Logout** — End the current session.

## 🛠️ Technologies Used

| Technology      | Purpose                            |
| --------------- | ---------------------------------- |
| Java            | Backend programming                |
| Spring Boot     | Web application framework          |
| Spring MVC      | Request handling and controllers   |
| Spring Data JPA | Database operations                |
| Hibernate       | Object-relational mapping          |
| Thymeleaf       | Dynamic HTML pages                 |
| HTML5           | Page structure                     |
| CSS3            | Styling and layout                 |
| JavaScript      | Client-side interactions           |
| MySQL           | Relational database                |
| Maven           | Dependency and project management  |
| IntelliJ IDEA   | Development environment            |
| Git and GitHub  | Version control and source hosting |

## 🏗️ Project Structure

```text
smartexpense/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── smartexpense/
│       │           ├── controller/
│       │           ├── model/
│       │           └── repository/
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   ├── js/
│           │   └── images/
│           ├── templates/
│           └── application.properties
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

*Note: The folder tree is a general guide. Your actual project may contain additional classes and files.*

## ⚙️ Prerequisites

Install the following before running the project:

* Java Development Kit (JDK 21 or a version supported by your project)
* IntelliJ IDEA
* MySQL Server
* MySQL Workbench (recommended)
* Git (optional if using IntelliJ's Git integration)

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/persephone-art/SmartExpense.git
cd SmartExpense
```


## 🗄️ Database Overview

The application uses MySQL to store financial data.

Typical tables include:

* `users` — User account information.
* `income` — Income entries associated with users.
* `expenses` — Expense entries associated with users.

The exact columns and relationships depend on the entity classes and database configuration in the project.

## 🧮 Tax Calculation Disclaimer

The Tax Comparison feature provides **illustrative estimates for educational purposes**. The current simplified calculations may not include all deductions, rebates, exemptions, cess, surcharges, or other applicable Indian income tax rules.

Do not use the estimates as a substitute for official tax calculations or professional tax advice.

## 🔒 Security Notes

* Keep database credentials and other secrets outside version control.
* Use separate user IDs when retrieving income and expense records.
* Verify authorization for every operation that reads, edits, or deletes financial records.
* Store passwords using a secure password-hashing algorithm.
* Do not store real users' financial information in public repositories.

## 🎯 Learning Objectives

This project demonstrates practical use of:

* Object-Oriented Programming concepts in Java.
* MVC architecture using Spring Boot.
* Database connectivity and CRUD operations.
* Entity and repository design using Spring Data JPA.
* Dynamic web pages with Thymeleaf.
* Session-based login and user-specific data retrieval.
* Financial calculations and report generation.
* Git and GitHub version control.

## 🔮 Future Enhancements

* Interactive charts and advanced financial analytics.
* Export reports to PDF or Excel.
* Improved validation and account recovery.
* More complete tax calculations based on current official rules.
* Budget planning and spending alerts.
* Automated testing and deployment.

## 👨‍💻 Author

**krishnapriya N**

Java OOP Project — Smart Expense and Income Tax Assistant

## 📄 License

No license has been selected yet. Unless a license is added, the repository should not be assumed to grant others permission to reuse, modify, or distribute the code.
