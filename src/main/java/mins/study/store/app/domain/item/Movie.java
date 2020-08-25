package mins.study.store.app.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter @Setter
@DiscriminatorValue(value = "MOVIE")
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
