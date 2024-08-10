package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String MY_SESSION_ID = "MY_SESSION_ID";//Ctrl+Alt+c로 문자열을 상수변수로 추가
    //저장할 해시 맵 생성
    private Map<String, Object> sessionStore=new ConcurrentHashMap<>();
    //동시에 많은 사람이 사용하기 위해 ConcurrentHashMap 적용

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response){
        //세션 id 생성하고 값을  세션에 저장
        String sessionId= UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(MY_SESSION_ID,sessionId);
        response.addCookie(mySessionCookie);
    }
    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies==null){
            return null;//쿠키 없음
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(MY_SESSION_ID)){//이름이 같은 쿠키를 찾으면
                return sessionStore.get(cookie.getValue());//쿠키의 값 리턴(uuid)
            }
        }
        return null;//이름이 같은 쿠키가 없으면 널
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){
        /*Cookie sessionCooki= (Cookie) getSession(request);// 왜인지 다운 케스팅 하면 에러 발생
        if(sessionCooki==null)  {
            sessionStore.remove(sessionCooki.getValue());//서버에 있는 세션 map을 한줄 제거
        }*/
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(MY_SESSION_ID)) {
                    sessionStore.remove(cookie.getValue());
                    break;
                }
            }
        }
    }
}
