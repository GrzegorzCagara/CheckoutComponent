package com.moler.technicaltask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundRunTimeException extends RuntimeException {
    public ItemNotFoundRunTimeException(String message) {
    }
}
