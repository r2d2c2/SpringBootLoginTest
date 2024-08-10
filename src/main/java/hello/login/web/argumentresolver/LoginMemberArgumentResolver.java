package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        //Login.class 어노테이션이 있는지 확인

        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        //Member.class 타입인지 확인
        return hasLoginAnnotation && hasMemberType;
        //둘다 만족하면 true
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //위에 supportsParameter가 true일때 실행
        log.info("resolveArgument 실행");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        //HttpServletRequest를 받아온다
        HttpSession session = request.getSession(false);//기본적응로 true의 값을 받아온다
        //세션을 받아온다
        if (session == null) {//세션이 없으면
            return null;
        }
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
        //세션에 있는 로그인 멤버를 반환
    }

}
