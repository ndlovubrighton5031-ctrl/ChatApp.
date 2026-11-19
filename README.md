# ChatApp - Full POE (Parts 1, 2 & 3)

## What is this app?
This is a Java console application built for the Programming 1 POE. It handles user registration, login, sending messages, storing messages in a JSON file, and managing stored messages using parallel arrays.

## Part 1: Registration and Login
*   **Username Check:** The system checks that the username has an underscore (`_`) and is no more than 5 characters long.
*   **Password Check:** The system checks that the password is at least 8 characters, has a capital letter, a number, and a special character.
*   **Cell Phone Check:** A regular expression (regex) validates that the cell number starts with `+27` and has exactly 9 digits after it.
*   **Login:** Once registered, the user must enter the exact same username and password to log in. If they get it wrong, the app loops and asks them to try again.

## Part 2: QuickChat Menu and Messaging
*   **Main Menu:** Displays options to Send Messages, Show recently sent messages, or Quit. It loops until the user quits.
*   **Sending Messages:** Asks the user how many messages they want to enter.
*   **Message Details:** Validates the recipient cell number and ensures the message text is 250 characters or less.
*   **Message ID and Hash:** Generates a random 10-digit ID for each message. It creates a hash using the first two ID digits, the message number, and the first and last words of the message (all in caps).
*   **Actions:** The user can choose to Send, Store (saves to `messages.json`), or Disregard each message.
*   **Total Counter:** Keeps track of the total number of messages processed.

## Part 3: Stored Messages and Arrays
*   **Parallel Arrays:** The app uses arrays to hold sent, stored, and disregarded messages, along with their IDs, hashes, and recipients.
*   **JSON Reading:** The `MessageStorage` class reads the `messages.json` file and loads the data into an array.
*   **Stored Messages Menu (Option 4):**
    1.  **Sender and Recipient:** Shows the logged-in user and the recipient of all stored messages.
    2.  **Longest Message:** Searches the array and displays the message with the most characters.
    3.  **Search by Message ID:** Looks up a specific ID and shows the recipient and message.
    4.  **Search by Recipient:** Finds and displays all messages sent to a specific number.
    5.  **Delete by Hash:** Removes a message from the JSON file and the arrays using its hash.
    6.  **Full Report:** Displays a detailed list of all stored messages including the hash, recipient, and message text.

## Unit Testing (JUnit)
The `ChatAppTest.java` file contains all the required tests for Parts 1, 2, and 3.

### Part 3 Test Data Used
I used the exact test data from the assignment brief to populate the arrays and check the logic:
*   **Message 1 (Sent):** `+27834557896`, "Did you get the cake?"
*   **Message 2 (Stored):** `+27838884567`, "Where are you? You are late! I have asked you to be on time."
*   **Message 3 (Disregard):** `+27834484567`, "Yohoooo, I am at your gate."
*   **Message 4 (Sent):** `0838884567`, "It is dinner time !"
*   **Message 5 (Stored):** `+27838884567`, "Ok, I am leaving without you."

### Part 3 Tests Included
*   `testSentMessagesArrayPopulated()`: Checks if the sent array has the correct text.
*   `testDisplayLongestMessage()`: Confirms the system finds the longest message.
*   `testSearchForMessageID()`: Confirms searching by ID gives the right message.
*   `testSearchMessagesByRecipient()`: Confirms searching by recipient returns all matching messages.
*   `testDeleteMessageByHash()`: Confirms the delete action removes the message and prints the exact success text.

## How to Run
1. Open the project in NetBeans or your preferred Java IDE.
2. Ensure the Gson library is added to your project classpath.
3. Run the `ChatApp.java` file.
4. Follow the console prompts.

## Attributions
*   **Gson Library:** Used for reading and writing JSON files. Reference provided in `MessageStorage.java`.
    *   Google, 2024. *Gson User Guide*. 
    *   Available at: https://github.com/google/gson/blob/main/UserGuide.md
