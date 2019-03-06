package edu.ecnu.scsse.pizza.consumer.server.exception;

import static edu.ecnu.scsse.pizza.consumer.server.exception.ExceptionType.BUSSINESS;

public class IllegalArgumentException extends ConsumerServerException {

    public IllegalArgumentException(String message, Throwable cause) {
        super(BUSSINESS, "非法参数！", message, cause);
    }

}
