# Finance Tracker
---
## 1. Executive Summary
Finance Tracker is an Android-based financial utility designed to help users track personal debts and receivables. Developed using Java and the Android Jetpack suite, the application provides a centralized interface for recording who owes money to the user and vice versa. By bridging the gap between casual verbal agreements and formal accounting, the app ensures financial transparency and reduces the risk of forgotten obligations.

---

## 2. Product Overview

### 2.1 Problem Statement

In personal and micro-business environments, tracking "who owes whom" is often managed through memory or disorganized notes. This leads to:  

•Forgotten debts.  
•Inaccurate balance tracking (mixing receivables and payables).  
•Lack of a clear timeline for due dates.

### 2.2 Key Features

•Dual Debt Tracking: Supports both "Owed To Me" (receivables) and "I Owe" (payables) categories.  

•Real-time Balance Calculation: Automatically calculates the net balance (Receivables - Payables) in KES (Kenyan Shillings).  

•Settlement Lifecycle: Allows users to mark debts as fully paid or track partial payments, with visual cues for settled items.  

•Detailed Records: Stores metadata including person names, amounts, due dates, payment methods (Cash, Mobile Money, Bank Transfer), and descriptions.  

•Local Persistence: Uses an on-device database to ensure data is accessible without an internet connection.

---
## 3. Technical Architecture

### 3.1 Tech Stack

•Language: Java  

•Database: SQLite via Room Persistence Library (an abstraction layer over SQLite).  

•UI Components: Material Design 3, RecyclerView, Floating Action Buttons (FAB), and ViewBinding/ConstraintLayouts.  

•Architecture Pattern: MVVM-ready (using LiveData and DAOs).

### 3.2 Database Schema (Room)

The application utilizes two primary entities managed by AppDatabase:  

1.debt Entity:  
◦Fields: debt_id (PK), person_name, amount, debt_type (Enum), status (Enum), date_created, is_settled, and amount_paid.  
◦Type Converters: A DateConverter utility is implemented to handle the conversion of Java Date objects into Long timestamps for SQLite storage.

2.User Entity:  
◦Stores profile information such as username, email, and default_currency (defaulting to "KES").

### 3.3 Core Logic: The DebtCalculator

The DebtCalculator.java utility acts as the financial engine. It iterates through all non-settled debts and performs the following calculation: $$\text{Total Balance} = \sum (\text{Remaining Owed To Me}) - \sum (\text{Remaining I Owe})$$ This ensures the user sees a single, actionable number on their dashboard.

---
## 4. Implementation Details

### 4.1 Data Access Objects (DAOs)

•DebtDao: Handles CRUD operations. It includes a specific @Query to mark a debt as paid, updating the is_settled flag, setting the status to 'PAID', and syncing the amount_paid to match the total amount in a single transaction.  

•UserDao: Uses LiveData to observe user profile changes, allowing the UI to reactively update the username or preferences.

### 4.2 User Interface & Interaction

•MainActivity: Serves as the primary dashboard. It observes debtDao.getAllDebts() using LiveData. Whenever the database changes, the DebtAdapter automatically refreshes the list, and the total balance is recalculated. 

•DebtAdapter: Implements a RecyclerView with custom listeners.  

◦Short Click: Triggers a detail dialog.  

◦Long Click: Opens an action menu (Mark as Paid/Delete).  

◦Visual Feedback: Settled debts are rendered with a 0.5f alpha (transparency) to distinguish them from active debts.  

•AddDebtActivity: Provides a form to input new records, utilizing RadioGroup for debt categorization and background threads for database insertion to prevent UI freezing.

---
## 5. Status and Enum Definitions

The project utilizes strongly-typed Enums to maintain data integrity:

•DebtType: OWED_TO_ME, I_OWE.

•DebtStatus: PENDING, PAID, PARTIALLY_PAID, OVERDUE.

•PaymentMethod: CASH, MOBILE_MONEY, BANK_TRANSFER.

---

## 6. Future Roadmap
 
1.Multi-Currency Support: Leveraging the currency field already present in the debt entity to support exchange rates.

2.Notification System: Implementing WorkManager to alert users when a due_date is approaching.

3.Search & Filter: Adding the ability to filter debts by status (e.g., viewing only unpaid items).

4.Contact Integration: Linking the contact_info field to the device's contacts for easier communication.

--- 

>Version: 1.0.0  
>Package: com.example.finance_tracker  
>Author: Technical Documentation Team