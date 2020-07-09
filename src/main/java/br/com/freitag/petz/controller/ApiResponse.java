package br.com.freitag.petz.controller;

public class ApiResponse<T> {

    private String message;
    private T result;

    public ApiResponse() {
	}
    
    public ApiResponse(String message, T result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}