package br.com.dauth.security;

import br.com.dauth.config.Text;
import br.com.dauth.dto.ErrorResponseDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setContentType(Text.APPLICATION_JSON);
        response.setCharacterEncoding(Text.UTF_8);
        response.setStatus(400);
        response.getWriter().println(ErrorResponseDto.builder().error(e.getMessage()).build().toString());
    }
}
