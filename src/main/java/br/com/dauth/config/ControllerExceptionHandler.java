package br.com.dauth.config;

import br.com.dauth.dto.ErrorResponseDto;
import br.com.dauth.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    private MessageService messageService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DException.class)
    public ErrorResponseDto handle(DException e, WebRequest request) {
        return ErrorResponseDto.builder().error(messageService.get(e.getMessage(), e.args)).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponseDto handle(MethodArgumentNotValidException e, WebRequest request) {
        var errorList = e.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ErrorResponseDto.builder().error(errorList.toString()).build();
    }
}
