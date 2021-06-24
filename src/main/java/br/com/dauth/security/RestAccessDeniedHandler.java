package br.com.dauth.security;

import br.com.dauth.config.Text;
import br.com.dauth.dto.ErrorResponseDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setContentType(Text.APPLICATION_JSON);
        response.setCharacterEncoding(Text.UTF_8);
        response.setStatus(400);
        response.getWriter().println(ErrorResponseDto.builder().error(e.getMessage()).build().toString());
    }
}
