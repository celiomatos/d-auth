package br.com.dauth.service;

import br.com.dauth.config.DException;
import br.com.dauth.config.Text;
import br.com.dauth.config.Utils;
import br.com.dauth.dto.AuthorityDto;
import br.com.dauth.dto.LoginResponseDto;
import br.com.dauth.dto.UserDto;
import br.com.dauth.filter.UserFilter;
import br.com.dauth.model.Authority;
import br.com.dauth.model.User;
import br.com.dauth.repository.AuthorityRepository;
import br.com.dauth.repository.UserRepository;
import br.com.dauth.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@CacheConfig(cacheNames = "permission")
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    public User dToM(UserDto dto) {
        var user = User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userFullName(dto.getUserFullName())
                .superUser(dto.isSuperUser())
                .build();
        if (Utils.isCollectionNotNullAndNotEmpty(dto.getAuthorities())) {
            user.setAuthorities(dto.getAuthorities().stream().map(a -> Authority.builder()
                    .id(a.getId())
                    .authority(a.getAuthority())
                    .build()).collect(Collectors.toList()));
        }
        return user;
    }

    public UserDto mToD(User entity) {
        var dto = UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userFullName(entity.getUserFullName())
                .superUser(entity.isSuperUser())
                .build();
        if (Utils.isCollectionNotNullAndNotEmpty(entity.getAuthorities())) {
            dto.setAuthorities(entity.getAuthorities().stream().map(a -> AuthorityDto.builder()
                    .id(a.getId())
                    .authority(a.getAuthority())
                    .build()).collect(Collectors.toList()));
        }
        return dto;
    }

    public void valid(UserDto dto) {
        var alreadyExist = repository.findByUsername(dto.getUsername());
        if (alreadyExist.isPresent() && !alreadyExist.get().getId().equals(dto.getId())) {
            throw new DException("username.not.available", dto.getUsername());
        }

        if (null == dto.getId() && !Utils.isNotNullAndNotEmpty(dto.getPassword())) {
            throw new DException("password.mandatory");
        }

        if (Utils.isNotNullAndNotEmpty(dto.getPassword())) {
            dto.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
            if (null != dto.getId()) repository.updatePassword(dto.getPassword(), dto.getId());
        }
    }

    public Page<UserDto> findAll(UserFilter f) {
        return repository.findAll(PageRequest.of(f.getPage(), f.getSize(), Sort.by("userFullName"))).map(this::mToD);
    }

    public UserDto findById(Integer id) {
        var m = repository.findById(id).orElseThrow(() -> new DException("registry.not.found"));
        return mToD(m);
    }

    @Transactional
    public UserDto save(UserDto d) {
        valid(d);
        var m = dToM(d);
        var s = repository.save(m);
        return mToD(s);
    }

    @Cacheable(value = Text.CACHE_PERMISSION)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username).orElseThrow(() -> new DException("user.not.found"));
        if (user.isSuperUser()) user.setAuthorities(authorityRepository.findAll());
        var dto = mToD(user);
        dto.setPassword(user.getPassword());
        return dto;
    }

    @CacheEvict(value = Text.CACHE_PERMISSION, key = "#username")
    public LoginResponseDto login(String username, String password) {
        var userPassword = new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(userPassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = (UserDto) authentication.getPrincipal();
        return LoginResponseDto.builder()
                .accessToken(jwtUtils.generateJwtToken(authentication))
                .name(user.getUserFullName())
                .authorities(user.getAuthorities())
                .build();
    }
}
