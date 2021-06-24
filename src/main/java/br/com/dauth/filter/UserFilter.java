package br.com.dauth.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilter {

    private Integer page = 0;

    private Integer size = 10;

}
