package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager=new SessionManager();

    @Test
    void createSession() {
        Member member=new Member();
        //Httpresponse, httprqust같은 겨우 톰켓에서 제공해 주는 웹 통신이라서 Mock이라는 별도의 테스트 코드를 사용해야한다
        MockHttpServletResponse response=new MockHttpServletResponse();
        sessionManager.createSession(member,response);//서버에서 세션과 쿠키 추가

        //요청의 응답 쿠키 저장 request(웹브라우저)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());//쿠키 이름과 uuid

        //서버에서 확인
        Object result = sessionManager.getSession(request);//쿠키이름과 ,uuid를 기반으로 대조 하여 멤버 정보 확인
        Assertions.assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();
    }

    @Test
    void getSession() {
    }

    @Test
    void expire() {
    }
}