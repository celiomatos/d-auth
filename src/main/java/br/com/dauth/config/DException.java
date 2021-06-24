package br.com.dauth.config;

public class DException extends RuntimeException {

    final String[] args;

    public DException(String message, String... args) {
        super(message);
        this.args = args.clone();
    }
}
