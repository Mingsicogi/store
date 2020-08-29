package mins.study.store.app.domain.item;

import lombok.Getter;
import lombok.Setter;
import mins.study.store.app.domain.Category;
import mins.study.store.app.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Entity
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 재고 증가
     *
     * @param quantity 수량
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     *
     * @param quantity 수량
     */
    public void removeStoke(int quantity) {
        if(this.stockQuantity - quantity < 0) {
            throw new NotEnoughStockException("Need more stock");
        }

        this.stockQuantity -= quantity;
    }
}
