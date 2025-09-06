# Loyalty Points System

This is a **command-line application** to manage customer loyalty points.  
It is built with **Java 21** and **Maven**.  
The code uses **Hexagonal Architecture** and **DDD ideas**.

---

## 🚀 Features

- `earn <customerId> <points>` → add points to customer
- `redeem <customerId> <points>` → remove points (not more than balance)
- `balance <customerId>` → show balance
- Interactive mode: you can type commands in a loop
- Warning when balance < 10 points:
