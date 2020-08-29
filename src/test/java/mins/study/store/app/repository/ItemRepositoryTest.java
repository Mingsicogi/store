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

import java.util.List;

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
        // GIVE
        Book book = new Book();
        book.setName("How to make code");
        book.setAuthor("MIN");
        book.setIsbn("123123-2994-dkjd-123");
        book.setPrice(50_000);
        itemService.saveItem(book);

        // WHEN
        Item dbInfo = itemService.findOne(book.getId());

        // THEN
        Assertions.assertEquals(book.getName(), dbInfo.getName());
        Assertions.assertEquals(book.getPrice(), dbInfo.getPrice());
    }

    @Test
    void findAll() {
        // GIVE
        Book book1 = new Book();
        book1.setName("How to make code");
        book1.setAuthor("MIN");
        book1.setIsbn("123123-2994-dkjd-123");
        book1.setPrice(50_000);
        itemService.saveItem(book1);

        Book book2 = new Book();
        book2.setName("How to manage project");
        book2.setAuthor("SEOK");
        book2.setPrice(60_000);
        itemService.saveItem(book2);

        // WHEN
        List<Item> items = itemService.findItems();

        // THEN
        Assertions.assertEquals(2, items.size());
        Assertions.assertTrue(items.stream().anyMatch(item -> book1.getName().equals(item.getName())));
        Assertions.assertTrue(items.stream().anyMatch(item -> book2.getName().equals(item.getName())));
    }
}