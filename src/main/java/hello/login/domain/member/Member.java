package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {
    private Long id;//테이블 키
    @NotEmpty
    private String  loginId;//유저 id
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
}
