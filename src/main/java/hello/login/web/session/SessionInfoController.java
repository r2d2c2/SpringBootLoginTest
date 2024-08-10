package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session==null){
            return "세션없음";
        }
        //세션에 있는 모든 것을 확인 하기
        session.getAttributeNames().asIterator()
                .forEachRemaining(name->log.info("세션이름={}, 값={}",name,session.getAttribute(name)));
        //로그인한 세션 이름과 세션 값 출력//세션이름=loginMember, 값=Member(id=1, loginId=test, name=태스터, password=test!)
        log.info("세션 id={}",session.getId());
        log.info("세션 getMaxInactiveInterval={}",session.getMaxInactiveInterval());
        //세션 유지시키는 시간(기본1800sec=30분)
        log.info("세션 getCreationTime={}",new Date(session.getCreationTime()));
        //세션 만든 시간
        log.info("세션 getLastAccessedTime={}",new Date(session.getLastAccessedTime()));
        //마지막으로 접근한 세션
        //세션유지에서 이 부분을 사용해서 30분 마다 로그인 하도록
        log.info("세션 isNew={}",session.isNew());
        //새거냐?
        return "세션 출력";
    }
}
