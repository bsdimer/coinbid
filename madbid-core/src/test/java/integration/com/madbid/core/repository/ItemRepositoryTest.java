package integration.com.madbid.core.repository;

import com.madbid.core.model.Item;
import com.madbid.core.repository.ItemRepository;
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
public class ItemRepositoryTest {
    public static final String TEST_ITEM_NAME = "Samsung Galaxy SII";

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateItem() {
        Item item = new Item();

        /* This properties are Depricated*/
        /*item.setRetailPrice(new BigDecimal(100));
        item.setShippingPrice(new BigDecimal(20));*/

        item.setName(TEST_ITEM_NAME);

        itemRepository.save(item);
        Item createdItem = itemRepository.findByName(TEST_ITEM_NAME);

        Assert.isTrue(item.getName().equals(createdItem.getName()));
    }
}
