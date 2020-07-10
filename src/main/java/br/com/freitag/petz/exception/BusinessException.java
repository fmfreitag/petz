package br.com.freitag.petz.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String message;
    public HttpStatus status;

    public BusinessException(String message) {
        super();
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
    }
    public BusinessException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
