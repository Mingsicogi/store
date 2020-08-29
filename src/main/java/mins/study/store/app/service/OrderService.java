package mins.study.store.app.service;

import lombok.RequiredArgsConstructor;
import mins.study.store.app.domain.*;
import mins.study.store.app.domain.item.Item;
import mins.study.store.app.repository.ItemRepository;
import mins.study.store.app.repository.MemberRepository;
import mins.study.store.app.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 회원과 상품정보 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 셋팅
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // order item 셋팅
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order); // Casecade ALL!! 관련 엔티티도 같이 insert됨.

        return order.getId();
    }

    // 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    //TODO 검색


}
