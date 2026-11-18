package chatapp;

import java.util.regex.Pattern;

public class Login {
    private final String[] userRecord = new String[5];
    private static final Pattern PHONE_REGEX = Pattern.compile("^\\+27[0-9]{9}$");

    // Validate username format
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Validate password rules
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        boolean upper = false, num = false, special = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) upper = true;
            else if (Character.isDigit(c)) num = true;
            else if (!Character.isLetterOrDigit(c)) special = true;
        }
        return upper && num && special;
    }

    // Validate SA phone number
    public boolean checkCellPhoneNumber(String phone) {
        return PHONE_REGEX.matcher(phone).matches();
    }

    // Generate registration message
    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        return "Username successfully captured.\nPassword successfully captured.";
    }

    // Store user data
    public void saveUser(String fName, String lName, String user, String pass, String cell) {
        userRecord[0] = fName;
        userRecord[1] = lName;
        userRecord[2] = user;
        userRecord[3] = pass;
        userRecord[4] = cell;
    }

    // Check login credentials
    public boolean loginUser(String username, String password) {
        return userRecord[2] != null && 
               userRecord[2].equals(username) && 
               userRecord[3].equals(password);
    }

    // Generate login status message
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + userRecord[0] + ", " + userRecord[1] + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}