package com.example.spring6.kj.controller;

public class ControllerException extends Exception {

    private final String message;

    public ControllerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
