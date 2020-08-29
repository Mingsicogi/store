package mins.study.store.app.service;

import mins.study.store.app.domain.Address;
import mins.study.store.app.domain.Member;
import mins.study.store.app.domain.Order;
import mins.study.store.app.domain.OrderStatus;
import mins.study.store.app.domain.item.Book;
import mins.study.store.app.domain.item.Item;
import mins.study.store.app.exception.NotEnoughStockException;
import mins.study.store.app.repository.ItemRepository;
import mins.study.store.app.repository.MemberRepository;
import mins.study.store.app.repository.OrderRepository;
import mins.testConfig.CustomTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@CustomTestConfig
@ContextConfiguration(classes = {OrderRepository.class, ItemRepository.class, MemberRepository.class})
class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    OrderService orderService;

    Address address = new Address("Seoul", "Jamsil", "123-222");
    Member member = new Member();
    Book book = new Book();
    int totalQuantityOfBook = 100_000;

    @BeforeEach
    public void init() {
        member.setName("minssogi");
        member.setAddress(address);
        memberRepository.save(member);

        book.setName("how to make good code");
        book.setPrice(15_000);
        book.setIsbn("192837-sjsjsjsj-eieieiei-123");
        book.setAuthor("MIN");
        book.setStockQuantity(totalQuantityOfBook);
        itemRepository.save(book);

        orderService = new OrderService(orderRepository, memberRepository, itemRepository);
    }

    @Test
    void order() {
        //GIVE
        int orderCount = 100;

        // WHEN
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // THEN
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(totalQuantityOfBook - orderCount, book.getStockQuantity());
        Assertions.assertEquals(book.getPrice() * orderCount, order.getTotalPrice());
        Assertions.assertEquals(OrderStatus.ORDER, order.getStatus());
    }

    @Test
    @DisplayName("재고 수량을 초과하는 주문")
    void orderOverTotalQuantity() {
        // GIVE
        int orderCount = 100_101;

        // WHEN & THEN
        Assertions.assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    @Test
    @DisplayName("재고 수량에 맞 주문")
    void orderEqualTotalQuantity() {
        // GIVE
        int orderCount = totalQuantityOfBook;

        // WHEN
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // THEN
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(totalQuantityOfBook - orderCount, book.getStockQuantity());
        Assertions.assertEquals(book.getPrice() * orderCount, order.getTotalPrice());
        Assertions.assertEquals(OrderStatus.ORDER, order.getStatus());
    }

    @Test
    void cancelOrder() {
        //GIVE
        int orderCount = 100;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // WHEN
        orderService.cancelOrder(orderId);

        // THEN
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(totalQuantityOfBook, book.getStockQuantity());
        Assertions.assertEquals(OrderStatus.CANCEL, order.getStatus());
    }
}