# ChatApp - Part 2

## Overview
This is a Java console application built for the Part 2 POE submission. It handles user registration, login, and the QuickChat messaging feature.

## Features
### Part 1: Registration and Login
*   **Username:** Validated to contain an underscore and be 5 characters or less.
*   **Password:** Validated for 8+ characters, a capital letter, a number, and a special character.
*   **Cell Phone:** Validated using regex for +27 and 9 digits.
*   **Login:** Loops until correct credentials are entered.

### Part 2: QuickChat Menu and Messaging
*   **Menu:** Displays options to send messages, show recently sent, or quit.
*   **Message Limits:** Enforces a 250-character limit and validates recipient numbers.
*   **Message ID & Hash:** Generates a random 10-digit ID and creates a hash based on the ID and message text.
*   **Actions:** Allows the user to Send, Store (saves to JSON), or Disregard messages.

## Testing
The `ChatAppTest.java` file contains all required unit tests for Parts 1 and 2. 
*   **Part 2 Tests:** Verifies recipient formats, message length limits, hash generation, and ID lengths.

## Attributions
*   **Gson Library:** Used for reading and writing JSON files. 
    *   Google, 2024. *Gson User Guide*. 
    *   Available at: https://github.com/google/gson/blob/main/UserGuide.md
