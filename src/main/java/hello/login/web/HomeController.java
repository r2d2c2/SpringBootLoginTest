package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/") //세션쿠키 적전
    public String home() {
        return "home";
    }
//    @GetMapping("/")
    public String homeLogin(@CookieValue(value = "memberId",required = false)Long memberId, Model model){
        //로그인 안한 사용자도 홈 화면을 볼수 있어야 하니 required=false
        if(memberId==null){//쿠키가 없으면
            return "home";//로그인,회원가입
        }
        Member loginMember = memberRepository.findById(memberId);//쿠키정보로 서버에서 사용자 로그인 가지고 오기
        if(loginMember==null){//서버에서 쿠키를 못찾으면
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){
        //세센 관리자로 정보 조회
        Member member=(Member)sessionManager.getSession(request);


        if(member==null){//서버에서 쿠키를 못찾으면
            return "home";
        }
        model.addAttribute("member",member);//널이 아니면 사용자 정보 출력
        return "loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model){
        //처음 로그인을 못한 유저방문시
        HttpSession session = request.getSession(false);
        if(session==null)
            return "home";

        Member longinMember = (Member) session.getAttribute(SessionManager.MY_SESSION_ID);

        if(longinMember==null){//서버에서 쿠키를 못찾으면
            return "home";
        }
        model.addAttribute("member",longinMember);//널이 아니면 사용자 정보 출력
        return "loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)Member longinMember, Model model){
        //스프링 간소화

        if(longinMember==null){//서버에서 쿠키를 못찾으면
            return "home";
        }
        model.addAttribute("member",longinMember);//널이 아니면 사용자 정보 출력
        return "loginHome";
    }
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member longinMember, Model model){
        //스프링 간소화

        if(longinMember==null){//서버에서 쿠키를 못찾으면
            return "home";
        }
        model.addAttribute("member",longinMember);//널이 아니면 사용자 정보 출력
        return "loginHome";
    }
}