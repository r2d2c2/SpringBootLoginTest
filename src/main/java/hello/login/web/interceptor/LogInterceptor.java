package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";
    //Ctrl+Alt+c 로 상수로 만들기

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //요청이 오기전에 먼저 실행
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);
        if(handler instanceof HandlerInterceptor){
            //호출되는 컨트롤러 메서드의 정보를 확인
            HandlerInterceptor handlerMethod = (HandlerInterceptor) handler;
        }
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;//true면 다음 인터셉터나 컨트롤러로 진행
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //컨트롤러 호출 이후 뷰 랜더링 하기 전에 실행
        log.info("postHandle modelAndView={}", modelAndView);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //뷰까지 처리가 끝난 후 실행
        String reString = request.getRequestURI().toString();
        Object logId = request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, reString,handler);
        if(ex!=null){//예외가 발생하면
            log.error("afterCompletion error!!",ex);
        }
    }
}
