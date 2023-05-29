package webChat.service.social;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.oauth2.core.user.OAuth2User;
import webChat.dto.ChatUserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// SpringSecurity 를 이용한 로그인 시 세션에 저장되는 UserDetails, OAuth2User 를 상속받은
// 구현 클래스
@Data
public class PrincipalDetails implements UserDetails {

    private ChatUserDto user;

    private String provider;

    public PrincipalDetails(ChatUserDto user, String provider) {
        this.user = user;
        this.provider = provider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> role = new ArrayList<>();

        role.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "user";
            }
        });

        return role;
    }

    @Override
    public String getPassword() {
        return "nopwd";
    }

    @Override
    public String getUsername() {
        return user.getNickName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
