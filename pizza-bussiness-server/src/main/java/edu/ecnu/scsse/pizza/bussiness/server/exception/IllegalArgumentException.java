package edu.ecnu.scsse.pizza.bussiness.server.exception;

import static edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType.BUSINESS;

public class IllegalArgumentException extends BusinessServerException{
    public IllegalArgumentException(String message, Throwable cause) {
        super(BUSINESS, "非法参数！", message, cause);
    }
}
