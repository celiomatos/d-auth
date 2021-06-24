package br.com.dauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements UserDetails {

    private Integer id;

    @NotBlank(message = "{username.mandatory}")
    @Size(max = 10, message = "{username.max.char}")
    private String username;

    @NotBlank(message = "{name.mandatory}")
    @Size(max = 100, message = "{name.max.char}")
    private String userFullName;

    @Size(max = 10, message = "{password.max.char}")
    private String password;

    private boolean superUser;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    private Collection<AuthorityDto> authorities;
}
