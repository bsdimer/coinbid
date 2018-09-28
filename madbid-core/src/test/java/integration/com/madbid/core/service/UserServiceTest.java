package integration.com.madbid.core.service;

import com.madbid.core.model.Coin;
import com.madbid.core.model.User;
import com.madbid.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * Created by nikolov.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/applicationContext-core.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@Transactional
public class UserServiceTest {
    public static final String TEST_ITEM_NAME = "test item";

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("Nikolov");
        user.setEmail("test@gmail.com");
        user.setMobileNumber("0883494949");
        user.setUsername("alexn");
        user.setPassword("alexn");
        user.setActive(true);
        // user.setCreatedDate(new LocalDateTime());
        user.setCoins(new ArrayList<Coin>());

        long usersCountBeforeSave = userService.count();
        userService.save(user);
        long usersCountAfterSave = userService.count();

        Assert.isTrue((usersCountBeforeSave + 1) == usersCountAfterSave);
    }
}
