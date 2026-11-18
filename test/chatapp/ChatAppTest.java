package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChatAppTest {

    private Login login;
    private MessageStorage store;

    @Before
    public void setup() {
        login = new Login();
        store = new MessageStorage();
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
}