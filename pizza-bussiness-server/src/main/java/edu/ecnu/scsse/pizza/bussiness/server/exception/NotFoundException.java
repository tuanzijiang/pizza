package edu.ecnu.scsse.pizza.bussiness.server.exception;

import static edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType.BUSINESS;

public class NotFoundException extends BusinessServerException {
    public NotFoundException(String message) {
        super(BUSINESS, "未找到相应数据！", message, null);
    }
}
