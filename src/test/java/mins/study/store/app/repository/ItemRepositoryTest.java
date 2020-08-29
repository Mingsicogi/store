package mins.study.store.app.repository;

import mins.study.store.app.domain.item.Book;
import mins.study.store.app.domain.item.Item;
import mins.study.store.app.service.ItemService;
import mins.testConfig.CustomTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@CustomTestConfig
@ContextConfiguration(classes = {ItemRepository.class})
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    ItemService itemService;

    @BeforeEach
    public void init() {
        itemService = new ItemService(itemRepository);
    }

    @Test
    void save() {
        // GIVE
        Book book = new Book();
        book.setName("How to make code");
        book.setAuthor("MIN");
        book.setIsbn("123123-2994-dkjd-123");
        book.setPrice(50_000);

        // WHEN
        itemService.saveItem(book);

        // THEN
        Assertions.assertNotNull(book.getId());

        Item dbInfo = itemRepository.findOne(book.getId());
        Assertions.assertEquals(book.getName(), dbInfo.getName());

    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }
}