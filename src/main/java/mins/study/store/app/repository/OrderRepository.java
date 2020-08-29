package mins.study.store.app.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import mins.study.store.app.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepository extends QuerydslRepositorySupport {

    private final EntityManager em;
    private final QOrder order = QOrder.order;
    private final QMember member = QMember.member;

    public OrderRepository(EntityManager em) {
        super(Order.class);
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return getQuerydsl().createQuery()
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()),
                        nameLike(orderSearch.getMemberName()))
                .fetch();
    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if(orderStatus != null) {
            return order.status.eq(orderStatus);
        }

        return null;
    }

    private BooleanExpression nameLike(String memberName) {
        if(StringUtils.isNoneBlank(memberName)) {
            return member.name.like(memberName);
        }

        return null;
    }
}
