package project.dailysup.account.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // used for serialized. (proxy)
public class SignUpRequestDto {

    @Length(min = 5, message = "ID는 5글자 이상이어야 합니다.")
    private String loginId;

    @Length(min = 5, message = "비밀번호는 5글자 이상이어야 합니다.")
    private String password;

    @Length(min = 5, message = "닉네임은 5글자 이상이어야 합니다.")
    private String nickname;


}
