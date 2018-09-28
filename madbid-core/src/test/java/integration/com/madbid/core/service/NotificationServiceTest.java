package integration.com.madbid.core.service;

import com.madbid.notification.exception.NotificationException;
import com.madbid.notification.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;

/**
 * Created by nikolov.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/applicationContext-core.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@Transactional
public class NotificationServiceTest {

    @Inject
    private NotificationService notificationService;

    @Test
    public void testCreateNotification() throws NotificationException {
        long countBeforeSave = notificationService.count();
//        notificationService.sendWelcomeEmail("alexUsername", "alex@gmail.com");
        long countAfterSave = notificationService.count();

        Assert.isTrue((countBeforeSave + 1) == countAfterSave);
    }
}
