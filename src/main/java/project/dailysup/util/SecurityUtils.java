package project.dailysup.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {

    private SecurityUtils(){}

    public static String getCurrentLoginId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            return null;
        }


        String loginId = null;
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            loginId = principal.getUsername();
        }

        return loginId;
    }

    public static Optional<String> getCurrentAccountRole(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            return Optional.empty();
        }

        String accountRole = null;

        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails principal = (UserDetails) authentication.getPrincipal();

            GrantedAuthority grantedAuthority = principal.getAuthorities()
                                                            .stream()
                                                            .findAny()
                                                            .orElseThrow(() -> new RuntimeException("인증정보를 찾을 수 없음"));
            accountRole = grantedAuthority.getAuthority();
        }

        return Optional.ofNullable(accountRole);
    }
}
