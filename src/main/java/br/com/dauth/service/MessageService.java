package br.com.dauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public String get(String key, String... args) {
        try {
            return messageSource.getMessage(key, args.clone(), new Locale("pt", "BR"));
        } catch (NoSuchMessageException e) {
            return key;
        }
    }
}
