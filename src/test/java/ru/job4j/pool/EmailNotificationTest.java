package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class for tests class EmailNotification.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 *
 * @see EmailNotification
 */
public class EmailNotificationTest {

    @Test
    public void whenSendEmailsThenAllOfThemSent() {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.emailTo(new User("Alex", "alex@mail.to"));
        emailNotification.emailTo(new User("Malex", "malex@mail.to"));
        emailNotification.emailTo(new User("Palex", "palex@mail.to"));
        emailNotification.close();
        assertTrue(emailNotification.allEmailsSent());
    }
}
