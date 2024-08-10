package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }
//    @PostMapping("login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null) {
            //bindingResult.rejectValue("loginId", "error.login.invalid");
            bindingResult.reject("loginFil","아이디 혹은 페스워드가 맞지 않습니다");
            return "login/loginForm";
        }
        //로그인 성공 처리 (세션쿠키 브라우저 종료시 모두 종료)
        Cookie memberId = new Cookie("memberId", String.valueOf(loginMember.getId()));//javax 쿠키
        response.addCookie(memberId);

        return "redirect:/";
    }
//    @PostMapping("login")
    public String loginV2(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null) {
            //bindingResult.rejectValue("loginId", "error.login.invalid");
            bindingResult.reject("loginFil","아이디 혹은 페스워드가 맞지 않습니다");
            return "login/loginForm";
        }
        //로그인 성공 처리 (세션쿠키 브라우저 종료시 모두 종료)

        sessionManager.createSession(loginMember,response);
        //세션 괸리자를 통해 세션을 생성 하고 회원 데이터 보관

        return "redirect:/";
    }

//    @PostMapping("login")
    public String loginV3(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null) {
            //bindingResult.rejectValue("loginId", "error.login.invalid");
            bindingResult.reject("loginFil","아이디 혹은 페스워드가 맞지 않습니다");
            return "login/loginForm";
        }
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();//false로 추가하면 세션이 없으면 null 반환
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return "redirect:/";
    }
    @PostMapping("login")
    public String loginV4(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request,
                          @RequestParam(defaultValue = "/") String redirectURL) {
        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null) {
            //bindingResult.rejectValue("loginId", "error.login.invalid");
            bindingResult.reject("loginFil","아이디 혹은 페스워드가 맞지 않습니다");
            return "login/loginForm";
        }
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();//false로 추가하면 세션이 없으면 null 반환
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return "redirect:"+redirectURL;//로그인 성공시 리다이렉트
    }
//    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("memberId", null);//쿠키 값 날리고
        cookie.setMaxAge(0);//쿠키 종료(웹 브라우저에서 제거)
        response.addCookie(cookie);
        return "redirect:/";
    }
//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);//request 변경하여 처리
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null)  {
            session.invalidate();//이미 세션이 있으면 제거
        }
        return "redirect:/";
    }
}
