package chatapp;

import java.util.Scanner;

public class ChatApp {
    private static final Scanner input = new Scanner(System.in);
    private static final Login login = new Login();
    private static final MessageStorage store = new MessageStorage();
    
    // Parallel arrays for Part 3
    private static String[] sentArr = new String[50];
    private static String[] discardArr = new String[50];
    private static String[] storedArr = new String[50];
    private static String[] hashArr = new String[50];
    private static String[] idArr = new String[50];
    private static String[] recArr = new String[50];
    private static int index = 0;

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("           REGISTRATION           ");
        System.out.println("==================================");

        String first = getText("Enter your first name: ");
        String last = getText("Enter your last name: ");
        String user = getValidUsername();
        String pass = getValidPassword();
        String cell = getValidCell();

        login.saveUser(first, last, user, pass, cell);
        System.out.println(login.registerUser(user, pass));

        while (true) {
            System.out.println("\n===========================");
            System.out.println("           LOGIN           ");
            System.out.println("===========================");
            String u = getText("Enter your username: ");
            String p = getText("Enter your password: ");
            
            if (login.loginUser(u, p)) {
                System.out.println(login.returnLoginStatus(true));
                showMainMenu();
                break;
            } else {
                System.out.println(login.returnLoginStatus(false));
            }
        }
        input.close();
    }

    // Read user input
    private static String getText(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    // Loop until valid username
    private static String getValidUsername() {
        while (true) {
            String u = getText("Enter your username: ");
            if (login.checkUserName(u)) return u;
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }
    }

    // Loop until valid password
    private static String getValidPassword() {
        while (true) {
            String p = getText("Enter your password: ");
            if (login.checkPasswordComplexity(p)) return p;
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
    }

    // Loop until valid cell number
    private static String getValidCell() {
        while (true) {
            String c = getText("Enter your cell phone number: ");
            if (login.checkCellPhoneNumber(c)) return c;
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }
    }

    // Display main menu
    private static void showMainMenu() {
        boolean active = true;
        while (active) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            String choice = getText("Choose an option: ");

            switch (choice) {
                case "1": createMessages(); break;
                case "2": System.out.println("Coming Soon."); break;
                case "3": active = false; System.out.println("Goodbye!"); break;
                case "4": showStoredMenu(); break;
                default: System.out.println("Invalid option. Please choose 1-4.");
            }
        }
    }

    // Handle message creation process
    private static void createMessages() {
        int total = 0;
        while (true) {
            try {
                total = Integer.parseInt(getText("How many messages do you wish to enter? "));
                if (total > 0) break;
                System.out.println("Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }

        int count = 0;
        while (count < total) {
            System.out.println("\n--- Message " + (count + 1) + " of " + total + " ---");
            
            String rec = "";
            do {
                rec = getText("Recipient cell number (e.g. +27838968976): ");
                Message m = new Message(count, rec, "test");
                if (m.checkRecipientCell()) break;
                System.out.println("Cell phone number incorrectly formatted or does not contain an international code.");
            } while (true);

            String text = "";
            do {
                text = getText("Message text: ");
                Message m = new Message(count, rec, text);
                if (m.checkMessageLength()) break;
                System.out.println("Message exceeds 250 characters or is empty.");
            } while (true);

            Message msg = new Message(count, rec, text);
            System.out.println("\n1) Send Message\n2) Store Message\n3) Disregard Message");
            String act = getText("Choose: ");

            if (act.equals("1")) {
                msg.setFlag("Sent");
                sentArr[index] = text;
                idArr[index] = msg.getMessageID();
                hashArr[index] = msg.getMessageHash();
                recArr[index] = rec;
                index++;
                System.out.println("Message sent.");
            } else if (act.equals("2")) {
                msg.setFlag("Stored");
                storedArr[index] = text;
                idArr[index] = msg.getMessageID();
                hashArr[index] = msg.getMessageHash();
                recArr[index] = rec;
                index++;
                store.saveMessage(msg);
                System.out.println("Message stored.");
            } else {
                msg.setFlag("Disregard");
                discardArr[index] = text;
                index++;
                System.out.println("Message disregarded.");
            }
            count++;
        }
        System.out.println("Total messages processed: " + index);
    }

    // Display stored messages menu
    private static void showStoredMenu() {
        boolean active = true;
        while (active) {
            System.out.println("\n=== STORED MESSAGES ===");
            System.out.println("1) Display sender and recipient");
            System.out.println("2) Display longest message");
            System.out.println("3) Search by Message ID");
            System.out.println("4) Search by recipient");
            System.out.println("5) Delete by Message Hash");
            System.out.println("6) Full Report");
            System.out.println("7) Back");
            String choice = getText("Choose: ");

            switch (choice) {
                case "1":
                    System.out.println("Sender: " + login.getFullName());
                    for (int i = 0; i < storedArr.length; i++) {
                        if (storedArr[i] != null) System.out.println("To: " + recArr[i] + " | Msg: " + storedArr[i]);
                    }
                    break;
                case "2":
                    String longest = "";
                    for (String s : storedArr) {
                        if (s != null && s.length() > longest.length()) longest = s;
                    }
                    System.out.println("Longest Message: " + longest);
                    break;
                case "3":
                    String searchId = getText("Enter Message ID: ");
                    boolean foundId = false;
                    for (int i = 0; i < idArr.length; i++) {
                        if (searchId.equals(idArr[i]) && storedArr[i] != null) {
                            System.out.println("To: " + recArr[i] + " | Msg: " + storedArr[i]);
                            foundId = true;
                        }
                    }
                    if (!foundId) System.out.println("Not found.");
                    break;
                case "4":
                    String searchRec = getText("Enter Recipient: ");
                    boolean foundRec = false;
                    for (int i = 0; i < recArr.length; i++) {
                        if (searchRec.equals(recArr[i]) && storedArr[i] != null) {
                            System.out.println("Msg: " + storedArr[i]);
                            foundRec = true;
                        }
                    }
                    if (!foundRec) System.out.println("No messages found.");
                    break;
                case "5":
                    String searchHash = getText("Enter Message Hash: ");
                    String deleted = store.deleteByHash(searchHash);
                    if (deleted != null) {
                        System.out.println("Message: \"" + deleted + "\" successfully deleted.");
                        for (int i = 0; i < hashArr.length; i++) {
                            if (searchHash.equals(hashArr[i])) {
                                storedArr[i] = null;
                                hashArr[i] = null;
                            }
                        }
                    } else {
                        System.out.println("Hash not found.");
                    }
                    break;
                case "6":
                    System.out.println("=== FULL REPORT ===");
                    for (int i = 0; i < storedArr.length; i++) {
                        if (storedArr[i] != null) {
                            System.out.println("Hash: " + hashArr[i] + " | To: " + recArr[i] + " | Msg: " + storedArr[i]);
                        }
                    }
                    break;
                case "7":
                    active = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1-7.");
            }
        }
    }
}