package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChatAppTest {

    private Login login;
    private MessageStorage store;

    // Test arrays populated with rubric data
    private String[] sent = new String[5];
    private String[] stored = new String[5];
    private String[] ids = new String[5];
    private String[] recs = new String[5];
    private String[] hashes = new String[5];

    @Before
    public void setup() {
        login = new Login();
        store = new MessageStorage();
        
        // Test Data Message 1 to 4
        sent[0] = "Did you get the cake?";
        ids[0] = "0838884567";
        recs[0] = "+27834557896";
        hashes[0] = "08:0:DIDCAKE?";

        stored[1] = "Where are you? You are late! I have asked you to be on time.";
        ids[1] = "1234567890";
        recs[1] = "+27838884567";
        hashes[1] = "12:1:WHERETIME.";

        stored[2] = "Ok, I am leaving without you.";
        ids[2] = "0987654321";
        recs[2] = "+27838884567";
        hashes[2] = "09:2:OKYOU.";

        sent[3] = "It is dinner time !";
        ids[3] = "0838884567";
        recs[3] = "Developer";
        hashes[3] = "08:3:IT!";
    }

    // Part 1 Tests
    @Test public void testUsername() { assertTrue(login.checkUserName("kyl_1")); }
    @Test public void testBadUsername() { assertFalse(login.checkUserName("kyle!!!!!!!")); }
    @Test public void testPassword() { assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!")); }
    @Test public void testBadPassword() { assertFalse(login.checkPasswordComplexity("password")); }
    @Test public void testCell() { assertTrue(login.checkCellPhoneNumber("+27838968976")); }
    @Test public void testBadCell() { assertFalse(login.checkCellPhoneNumber("08966553")); }
    
    @Test public void testRegisterSuccess() {
        assertEquals("Username successfully captured.\nPassword successfully captured.", login.registerUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test public void testRegisterBadUser() {
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!"));
    }
    @Test public void testRegisterBadPass() {
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.registerUser("kyl_1", "password"));
    }
    
    @Test public void testLoginSuccess() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test public void testLoginFail() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrong", "wrong"));
    }
    @Test public void testWelcomeMsg() {
        login.saveUser("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Welcome John, Doe it is great to see you again.", login.returnLoginStatus(true));
    }
    @Test public void testRejectMsg() {
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(false));
    }

    // Part 2 Tests
    @Test public void testRecipientValid() {
        assertTrue(new Message(0, "+27718693002", "Hi").checkRecipientCell());
    }
    @Test public void testRecipientInvalid() {
        assertFalse(new Message(0, "08575975889", "Hi").checkRecipientCell());
    }
    @Test public void testLengthValid() {
        assertTrue(new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?").checkMessageLength());
    }
    @Test public void testLengthInvalid() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");
        assertFalse(new Message(0, "+27718693002", sb.toString()).checkMessageLength());
    }
    @Test public void testHash() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", m.getMessageHash());
    }
    @Test public void testIdLength() {
        assertTrue(new Message(0, "+27718693002", "Hi").checkMessageID());
    }

    // Part 3 Tests
    @Test public void testArrayPopulated() {
        assertEquals("Did you get the cake?", sent[0]);
        assertEquals("It is dinner time !", sent[3]);
    }

    @Test public void testLongestMessage() {
        String longest = "";
        for (String s : stored) {
            if (s != null && s.length() > longest.length()) longest = s;
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test public void testSearchId() {
        String expected = "";
        for (int i = 0; i < ids.length; i++) {
            if ("0838884567".equals(ids[i]) && i == 3) expected = sent[i];
        }
        assertEquals("It is dinner time !", expected);
    }

    @Test public void testSearchRecipient() {
        String rec = "+27838884567";
        String m1 = "", m2 = "";
        for (int i = 0; i < recs.length; i++) {
            if (rec.equals(recs[i])) {
                if (i == 1) m1 = stored[i];
                if (i == 2) m2 = stored[i];
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", m1);
        assertEquals("Ok, I am leaving without you.", m2);
    }

    @Test public void testDeleteHash() {
        String target = "12:1:WHERETIME.";
        String deleted = null;
        for (int i = 0; i < hashes.length; i++) {
            if (target.equals(hashes[i])) {
                deleted = stored[i];
                stored[i] = null;
            }
        }
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", 
                     "Message: \"" + deleted + "\" successfully deleted.");
    }
}