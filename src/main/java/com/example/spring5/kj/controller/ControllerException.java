package com.example.spring5.kj.controller;

public class ControllerException extends Exception {

    private final String message;

    ControllerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
