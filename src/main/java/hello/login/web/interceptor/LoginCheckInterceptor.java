package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    //afterCompletion, postHandle, preHandle 다 디폴트 문이라서 생략 가능하다(필요한 부분만 사용가능)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //요청이 오기전에 먼저 실행
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            //다시 로그인 페이지로
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;//더이상 진행하지 않는다
        }
        return true;
    }
}
