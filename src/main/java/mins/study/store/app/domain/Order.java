package mins.study.store.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mins.study.store.app.exception.BadRequestException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// default constructor를 사용하지 못하게 막음. 개발자가 의도한 생성만 가능하게 함.
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    // setter
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // 연관 관계 편의 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        this.getOrderItems().add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 주문 생성 메소드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderDate(LocalDateTime.now());

        for (OrderItem orderItem : orderItems) {
            order.getOrderItems().add(orderItem);
        }

        return order;
    }

    // 주문 취소
    public void cancel() {
        if(OrderStatus.COMPLETE == this.status) {
            throw new BadRequestException("Cannot cancel order you request because of already arrived item that you ordered.");
        }

        this.getOrderItems().forEach(OrderItem::cancel);
    }

    // 전체 주문 가격 조회
    public int getTotalPrice() {
        return this.getOrderItems().stream().mapToInt(OrderItem::getOrderTotalPrice).sum();
    }
}
