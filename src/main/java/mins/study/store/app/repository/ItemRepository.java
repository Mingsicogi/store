package mins.study.store.app.repository;

import lombok.RequiredArgsConstructor;
import mins.study.store.app.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * 단건 저장
     *  - 변경 감지와 merge
     *   변경 감지 : 영속 상태 객체를 변경하고 트랜잭션 커밋시 flush를 통해 dirty checking된 필드를 업데이트 함.
     *   merge : 준영속 상태 객체를 전달해 entityManager 에 merge 메소드를 통해 해당 필드를 업데이트 함.
     *
     *    *** 같은 변경이지만 둘의 큰 차이는 변경 감지의 경우 변경된 필드에 대해서만 업데이트를 하지만 merge는 전달 받은 object 전체를 업데이트 함.
     *    *** 즉, merge의 경우 전달 받은 object 필드에 null값이 존재 하는 경우 의도치 않게 데이터가 null로 업데이트 될 수 있음. 실무에선 영속화 된 엔티티를 통해 변경 감지 기능 활용!!
     *
     * @param item
     */
    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
