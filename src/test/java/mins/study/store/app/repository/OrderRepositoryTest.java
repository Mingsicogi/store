package mins.study.store.app.repository;

import mins.study.store.app.domain.*;
import mins.study.store.app.domain.item.Book;
import mins.study.store.app.domain.item.Item;
import mins.testConfig.CustomTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CustomTestConfig
@ContextConfiguration(classes = {OrderRepository.class, ItemRepository.class, MemberRepository.class})
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    Address address = new Address("Seoul", "Jamsil", "123-222");
    Member member = new Member();
    Book book = new Book();

    @BeforeEach
    public void init() {
        member.setName("minssogi");
        member.setAddress(address);
        memberRepository.save(member);

        book.setName("how to make good code");
        book.setPrice(15_000);
        book.setIsbn("192837-sjsjsjsj-eieieiei-123");
        book.setAuthor("MIN");
        book.setStockQuantity(100_000);
        itemRepository.save(book);
    }

    @Test
    void save() {
        // GIVE
        OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), 10);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        Order order = Order.createOrder(member, delivery, orderItem);

        // WHEN
        orderRepository.save(order);

        // THEN
        Order dbInfoOrder = orderRepository.findOne(order.getId());
        Assertions.assertTrue(dbInfoOrder.getOrderItems().stream().anyMatch(orderedItem -> book.getName().equals(orderedItem.getItem().getName())));
    }

    @Test
    void findOne() {
        // GIVE
        OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), 10);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        Order order = Order.createOrder(member, delivery, orderItem);

        // WHEN
        orderRepository.save(order);

        // THEN
        Order dbInfoOrder = orderRepository.findOne(order.getId());
        Assertions.assertTrue(dbInfoOrder.getOrderItems().stream().anyMatch(orderedItem -> book.getName().equals(orderedItem.getItem().getName())));
    }

    @Test
    void findAll() {
        // GIVE
        OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), 10);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        OrderSearch orderSearch = new OrderSearch();
//        orderSearch.setMemberName("minssogi");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        // WHEN
        List<Order> orders = orderRepository.findAll(orderSearch);

        // THEN
        Assertions.assertTrue(orders.stream().anyMatch(findOrder -> member.getName().equals(findOrder.getMember().getName())));
    }
}