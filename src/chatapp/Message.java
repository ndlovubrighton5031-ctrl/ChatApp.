package chatapp;

import java.util.concurrent.ThreadLocalRandom;

public class Message {
    private String messageID, recipient, messageText, messageHash, flag;
    private int messageNumber;

    // Setup message and generate ID/hash
    public Message(int num, String rec, String text) {
        this.messageNumber = num;
        this.recipient = rec;
        this.messageText = text;
        this.flag = "Pending";
        generateID();
        generateHash();
    }

    // Setup message with specific ID for testing
    public Message(int num, String rec, String text, String forcedID) {
        this.messageNumber = num;
        this.recipient = rec;
        this.messageText = text;
        this.messageID = forcedID;
        this.flag = "Pending";
        generateHash();
    }

    // Generate random 10-digit ID
    private void generateID() {
        long id = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
        this.messageID = String.valueOf(id);
    }

    // Create hash using ID, message number, and first/last words
    private void generateHash() {
        if (messageID == null || messageID.length() < 2) {
            this.messageHash = "";
            return;
        }
        String prefix = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
        String last = words.length > 1 ? words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase() : "";
        this.messageHash = prefix + ":" + messageNumber + ":" + first + last;
    }

    // Check ID length
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // Check recipient format
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+[0-9]{10,12}$");
    }

    // Check message length
    public boolean checkMessageLength() {
        return messageText.length() <= 250 && !messageText.isEmpty();
    }

    // Format message details for display
    public String printMessage() {
        return String.format("Message ID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s", 
                             messageID, messageHash, recipient, messageText);
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
}