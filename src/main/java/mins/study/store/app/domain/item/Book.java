package mins.study.store.app.domain.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter @Setter
@DiscriminatorValue(value = "BOOK")
@Entity
public class Book extends Item {
    private String author;
    private String isbn;

    public void updateBook(String author, String isbn, String name, int price, int stockQuantity) {
        super.updateItem(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
