# OOP Bank Simulation System

This repository contains a two-part Object-Oriented Programming (OOP) banking simulation project. It demonstrates the iterative development of a system, evolving from basic inheritance models to an advanced architecture utilizing abstract classes and custom exception handling.

## 📁 Project Structure

The project is divided into two main phases:

### Phase 1: Basic OOP Concepts (`Part1-Basic-OOP` folder)
The foundational version of the banking system.
* **Concepts:** Inheritance, Polymorphism, Method Overriding.
* **Features:** * Base `BankAccount` and `Bank` structures.
  * `CheckingAccount` with overdraft capabilities.
  * `CommissionBank` that automatically deducts a percentage from successful transfers.

### Phase 2: Advanced Architecture (`Part2-Advanced-Exceptions` folder)
An upgraded and refactored version of the system with stricter rules and error handling.
* **Concepts:** Abstraction, Custom Exception Handling (`BankException`).
* **Features:**
  * `BankAccount` and `Bank` refactored as **Abstract Classes**.
  * Introduction of `FXAccount` which handles foreign exchange rates during deposits and withdrawals.
  * `MinCommissionBank` which applies minimum threshold rules for transfer deductions.
  * A robust custom exception system that gracefully catches and formats invalid operations (e.g., negative amounts, inadequate funds).
