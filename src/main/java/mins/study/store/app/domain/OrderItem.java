package mins.study.store.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mins.study.store.app.domain.item.Item;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// default constructor를 사용하지 못하게 막음. 개발자가 의도한 생성만 가능하게 함.
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // setter
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void setItem(Item item) {
        this.item = item;
    }

    // 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStoke(count); // 재고 차감

        return orderItem;
    }

    // 총 주문 금액 조회
    public int getOrderTotalPrice() {
        return orderPrice * count;
    }

    // 주문 취소
    public void cancel() {
        this.item.addStock(this.count);
    }
}
