package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //이건 핸들러 메소드의 파라미터를 추가하는 부분
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //이건 인터셉터를 등록하는 부분
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")//모든 URL에 적용 //서블릿은 /*로 적용
                .excludePathPatterns("/css/**", "*.ico", "/error");//로그인이 필요없는 URL
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")//모든 URL에 적용
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "*.ico", "/error");
        //로그인이 필요없는 URL
    }
//    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new LogFilter());//필터 등록
        filterRegistrationBean.setOrder(1);//필터 순서
        filterRegistrationBean.addUrlPatterns("/*");//모든 URL에 적용
        return filterRegistrationBean;
    }
//    @Bean
    public FilterRegistrationBean logCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());//필터 등록
        filterRegistrationBean.setOrder(2);//필터 순서
        filterRegistrationBean.addUrlPatterns("/*");//모든 URL에 적용
        return filterRegistrationBean;
    }


}
