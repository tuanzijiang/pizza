package edu.ecnu.scsse.pizza.consumer.server.exception;

public class ThirdPartyException extends ConsumerServerException {

    public ThirdPartyException(String hintMessage, String message) {
        super(ExceptionType.THIRD_PARTY, hintMessage, message);
    }

    public ThirdPartyException(String hintMessage, String message, Throwable cause) {
        super(ExceptionType.THIRD_PARTY, hintMessage, message, cause);
    }
}
