package mins.study.store.app.repository;

import mins.study.store.app.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {MemberRepository.class})
@EntityScan(basePackages = {"mins.study.store.app.entity"})
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    void save() {
        Member member = new Member();
        member.setName("A");

        Long memberId = memberRepository.save(member);

        Member dbInfo = memberRepository.find(memberId);
        Assertions.assertEquals(member.getName(), dbInfo.getName());
    }

    @Test
    void find() {
    }
}