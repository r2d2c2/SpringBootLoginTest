package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {//init, destroy 디폴트라서 생략
    private static final String[] whitelist={"/","/members/add","/login","/logout","/css/*"};
    //로그인이 필요없는 URL
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest= (HttpServletRequest) servletRequest;
        String requestURI=httpRequest.getRequestURI();
        HttpServletResponse httpResponse=(HttpServletResponse) servletResponse;
        try{
            log.info("인증 체크 필터 시작 [{}]", requestURI);
            if(isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 [{}]", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session==null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    //세션, 로그인 정보가 없으면
                    log.info("미인증 사용자 요청 [{}]", requestURI);
                    //로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return;
                }
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e) {
            throw e;
        }finally {
            log.info("인증 체크 필터 종료 [{}][{}]", requestURI);
        }
        /**
        *화이트 리스트의 경우 인증 체크 X
        */
    }
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist,requestURI);
        //화이트리스트에 포함되어 있으면 false
    }
}
