package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//파라미터에만 사용가능
@Retention(RetentionPolicy.RUNTIME)//런타임까지 유지
public @interface Login {//로그인한 사용자 정보를 찾아주는 어노테이션
    // @interface는 어노테이션을 만들때 사용하는 키워드

}
