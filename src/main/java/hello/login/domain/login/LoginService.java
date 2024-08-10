package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    /**
     *  retun null이면 실팽
     */
    public Member login(String loginId, String password) {
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        return findMember.filter(m->m.getPassword().equals(password))
              .orElse(null);
    }
}
