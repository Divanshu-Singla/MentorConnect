# 🎓 Mentor Connect – Java Command Line Project

Mentor Connect is a **Java-based command-line application** developed to strengthen understanding of **Object-Oriented Programming (OOPS)** and **Data Structures & Algorithms (DSA)**.  
The project simulates a real-world **Mentor–Mentee Management System**, allowing mentors and mentees to register, connect, communicate, and manage assignments.

---

## 🚀 Project Objective

The primary goal of this project is to:
- Apply **core Java concepts**
- Implement **DSA algorithms** in a practical system
- Understand **real-world object relationships**
- Build logic without using any external frameworks or databases

---

## 🧠 Concepts Covered

### 🔹 Object-Oriented Programming (OOPS)
- Abstraction (Abstract User class)
- Inheritance (Mentor & Mentee extending User)
- Encapsulation (User data and behavior)
- Polymorphism (Common message-handling behavior)

### 🔹 Data Structures Used
- `ArrayList` – storing mentors and mentees
- `HashMap` – message storage & request mapping
- `LinkedList` – assignment request queue (FIFO)
- Custom objects as keys and values

### 🔹 Algorithms Implemented
- **Merge Sort**
  - Sorting mentees by name
  - Sorting mentees by roll number
- **Quick Sort**
  - Sorting mentors by name
  - Sorting mentors by subject
  - Sorting mentors by mentor ID
- **Binary Search**
  - Searching mentees by roll number
  - Searching mentors by mentor ID

---

## ✨ Features

### 👤 Authentication System
- Mentor Sign Up / Sign In
- Mentee Sign Up / Sign In
- Unique Mentor ID & Roll Number validation

### 🔄 Mentor–Mentee Assignment
- Mentees can send assignment requests
- Mentors can accept or reject requests
- FIFO request handling using LinkedList

### 📊 Sorting & Searching
- View sorted mentors and mentees
- Binary search for fast lookup
- Tabular console output for better readability

### 💬 Messaging System
- Two-way messaging between mentors and mentees
- Messages stored using HashMap
- Read individual or all messages

### 📋 Console UI
- Menu-driven system
- Clean table-format output
- User-friendly CLI navigation

---

## 🛠️ Tech Stack

| Technology | Usage |
|----------|------|
| Java | Core language |
| Scanner | User input |
| Java Collections | Data storage |
| DSA Algorithms | Sorting & Searching |
| CLI | User interaction |

---

## 📂 Project Structure

```
mentor-connect/
│
├── MentorMenteeManagementSystem.java
└── README.md
```

---

## ▶️ How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/mentor-connect.git
   ```

2. Navigate to the project folder:
   ```bash
   cd mentor-connect
   ```

3. Compile the program:
   ```bash
   javac MentorMenteeManagementSystem.java
   ```

4. Run the program:
   ```bash
   java MentorMenteeManagementSystem
   ```

---

## 📸 Screenshots

*Add screenshots of your console UI here*

---

## 🎯 Learning Outcomes

- Understanding of **OOPS principles** in real applications
- Practical implementation of **DSA concepts**
- Console-based user interface design
- Data management without databases
- Algorithm optimization techniques

