package edu.ecnu.scsse.pizza.consumer.server.exception;

public class ServiceException extends ConsumerServerException {

    public ServiceException(String message, Throwable cause) {
        super(ExceptionType.SERVICE, message, cause);
    }

}
