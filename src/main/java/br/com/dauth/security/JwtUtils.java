package br.com.dauth.security;

import br.com.dauth.config.Text;
import br.com.dauth.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {

        var cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis() + Text.THIRTY_DAYS);

        return Jwts.builder()
                .setSubject(((UserDto) authentication.getPrincipal()).getUsername())
                .setIssuedAt(new Date())
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
