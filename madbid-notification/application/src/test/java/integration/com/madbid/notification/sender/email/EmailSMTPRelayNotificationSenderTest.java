package integration.com.madbid.notification.sender.email;

import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.sender.email.smtp.EmailSMTPRelayNotificationSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nikolov.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext-notification-app.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@Transactional
public class EmailSMTPRelayNotificationSenderTest {
    public static final String EMAIL_RECEIVER = "AlexanderNikolovNikolov@gmail.com";

    @Autowired
    private EmailSMTPRelayNotificationSender emailSMTPRelayNotificationSender;

    @Test
    public void testSendingOfDummyEmail() {
        Notification notification = new Notification();
        notification.setType(NotificationType.EMAIL_SMTP_RELAY);
        notification.setReceiver(EMAIL_RECEIVER);
        notification.setSubject("Test Notifications");
        notification.setText("test notification ....");

        emailSMTPRelayNotificationSender.sendNotification(notification);
        //This is dummy test, no asserts - check your email :)
    }
}
