package edu.ecnu.scsse.pizza.consumer.server.exception;

import static edu.ecnu.scsse.pizza.consumer.server.exception.ExceptionType.BUSSINESS;

public class NotFoundException extends ConsumerServerException {

    private static final String HINT = "未找到相应数据！";

    public NotFoundException(String message) {
        super(BUSSINESS, HINT, message, null);
    }
    public NotFoundException(String messageTemplate, Object... args) {
        super(BUSSINESS, HINT, String.format(messageTemplate, args), null);
    }
}
