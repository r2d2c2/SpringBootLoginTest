package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<Long, Member>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save : memberid={}",member);
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(long id) {
        return store.get(id);//아이디 찾기
    }
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public Optional< Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                //조건에 맞으면 맞는 값만 전달
                .findFirst();

    }
    public void cleaerStore(){
        store.clear();
    }
}
