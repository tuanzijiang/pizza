package edu.ecnu.scsse.pizza.bussiness.server.exception;

import static edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType.CONTROLLER;

public class PermissionException extends BusinessServerException {
    public PermissionException(String message) {
        super(CONTROLLER, "管理员已登出，需要重新登录！", message, null);
    }
}
