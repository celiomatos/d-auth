package br.com.dauth.controller;

import br.com.dauth.dto.LoginResponseDto;
import br.com.dauth.dto.UserDto;
import br.com.dauth.filter.UserFilter;
import br.com.dauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public LoginResponseDto login(@RequestParam String username, @RequestParam String password) {
        return service.login(username, password);
    }

    @GetMapping
    public Page<UserDto> findAll(@Valid UserFilter f) {
        return service.findAll(f);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto d) {
        return service.save(d);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UserDto d) {
        return service.save(d);
    }

}
