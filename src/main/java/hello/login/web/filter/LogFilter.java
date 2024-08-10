package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter.init");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LogFilter.doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();//모든 URI를 받고
        String  uuid = UUID.randomUUID().toString();//랜덤한 UUID를 생성
        try{
            log.info("REQUEST [{}][{}]", uuid, requestURI);//UUID와 URI를 출력
            //REQUEST [459f613a-93a2-47e6-8cbd-a039e6540285][/items]
            filterChain.doFilter(servletRequest, servletResponse);
            //다음 필터로 넘어가기 이부분이 없으면 다음으로 넘어가지 않음!!
        }catch (Exception e) {
            throw e;
        }finally {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
        }
    }
    @Override
    public void destroy() {
        log.info("LogFilter.destroy");
    }
}
