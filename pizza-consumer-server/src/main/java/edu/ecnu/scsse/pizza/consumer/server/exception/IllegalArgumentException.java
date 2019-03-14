package edu.ecnu.scsse.pizza.consumer.server.exception;

import static edu.ecnu.scsse.pizza.consumer.server.exception.ExceptionType.BUSSINESS;

public class IllegalArgumentException extends ConsumerServerException {

    private static final String HINT = "非法参数！";

    public IllegalArgumentException(String message) {
        super(BUSSINESS, HINT, message);
    }

    public IllegalArgumentException(String message, Throwable cause) {
        super(BUSSINESS, HINT, message, cause);
    }

    public IllegalArgumentException(String messageTemplate, Object... args) {
        super(BUSSINESS, HINT, String.format(messageTemplate, args));
    }


}
