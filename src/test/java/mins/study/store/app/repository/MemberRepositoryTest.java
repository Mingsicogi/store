package mins.study.store.app.repository;

import mins.study.store.app.domain.Address;
import mins.study.store.app.domain.Member;
import mins.study.store.app.exception.DuplicationException;
import mins.study.store.app.service.MemberService;
import mins.testConfig.CustomTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@CustomTestConfig
@ContextConfiguration(classes = {MemberRepository.class})
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    MemberService memberService;

    @BeforeEach
    public void init() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("save success test")
    void save() {
        // GIVE
        Member member = new Member();
        member.setName("minssogi");
        member.setAddress(new Address("Seoul", "123", "aa-123"));

        // WHEN
        memberService.join(member);

        // THEN
        Assertions.assertNotNull(member.getId());
        Assertions.assertTrue(member.getId() > 0);
    }

    @Test
    void save_duplication_name() {
        // GIVE
        Member member1 = new Member();
        member1.setName("minssogi");
        member1.setAddress(new Address("Seoul", "123", "aa-123"));
        Member member2 = new Member();
        member2.setName("minssogi");
        member2.setAddress(new Address("Seoul", "123", "aa-123"));

        // WHEN
        memberService.join(member1);

        // THEN
        Assertions.assertThrows(DuplicationException.class, () -> memberService.join(member2));
    }
}