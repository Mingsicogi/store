package mins.study.store.app.service;

import lombok.RequiredArgsConstructor;
import mins.study.store.app.domain.Member;
import mins.study.store.app.exception.DuplicationException;
import mins.study.store.app.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {

        validateDuplicationMember(member);

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicationMember(Member member) {
        if(!memberRepository.findByName(member.getName()).isEmpty()) {
            throw new DuplicationException("Duplication Member Name!!");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }
}
