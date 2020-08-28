package mins.study.store.app.repository;

import mins.study.store.app.domain.Address;
import mins.study.store.app.domain.Member;
import mins.study.store.app.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {MemberRepository.class})
//@EntityScan(basePackages = {"mins.study.store.app.domain"})
@Transactional
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
}