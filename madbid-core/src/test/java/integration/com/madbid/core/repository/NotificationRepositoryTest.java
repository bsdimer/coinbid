package integration.com.madbid.core.repository;

import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.repository.NotificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by nikolov.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/applicationContext-core.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@Transactional
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void testCreateNotification() {
        Notification notification = new Notification();
        notification.setText("Test Message");
        notification.setSender("Madbid");
        notification.setReceiver("alex@gmail.com");
        notification.setSubject("Madbid subject");
        notification.setType(NotificationType.EMAIL_SMTP_RELAY);

        long countBefore = notificationRepository.count();
        notificationRepository.save(notification);
        long countAfter = notificationRepository.count();

        Assert.isTrue((countBefore + 1) == countAfter);
    }
}
