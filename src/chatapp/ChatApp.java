package chatapp;

import java.util.Scanner;

public class ChatApp {
    private static final Scanner input = new Scanner(System.in);
    private static final Login login = new Login();
    private static final MessageStorage store = new MessageStorage();
    private static int totalMessagesSent = 0;

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
            String choice = getText("Choose an option: ");

            switch (choice) {
                case "1": createMessages(); break;
                case "2": System.out.println("Coming Soon."); break;
                case "3": active = false; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option. Please choose 1-3.");
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
            
            // Loop until recipient is valid
            String rec = "";
            do {
                rec = getText("Recipient cell number (e.g. +27838968976): ");
                Message m = new Message(count, rec, "test");
                if (m.checkRecipientCell()) break;
                System.out.println("Cell phone number incorrectly formatted or does not contain an international code.");
            } while (true);

            // Loop until message text is valid
            String text = "";
            do {
                text = getText("Message text: ");
                Message m = new Message(count, rec, text);
                if (m.checkMessageLength()) break;
                System.out.println("Message exceeds 250 characters or is empty.");
            } while (true);

            Message msg = new Message(count, rec, text);
            System.out.println("\n" + msg.printMessage());
            
            System.out.println("\n1) Send Message\n2) Store Message\n3) Disregard Message");
            String act = getText("Choose: ");

            // Process user choice
            if (act.equals("1")) {
                msg.setFlag("Sent");
                totalMessagesSent++;
                System.out.println("Message sent.");
            } else if (act.equals("2")) {
                msg.setFlag("Stored");
                store.saveMessage(msg);
                totalMessagesSent++;
                System.out.println("Message stored.");
            } else {
                msg.setFlag("Disregard");
                System.out.println("Message disregarded.");
            }
            count++;
        }
        System.out.println("Total messages processed: " + totalMessagesSent);
    }
}