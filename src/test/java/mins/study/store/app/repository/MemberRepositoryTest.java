package mins.study.store.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {MemberRepository.class})
@EntityScan(basePackages = {"mins.study.store.app.entity"})
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


}