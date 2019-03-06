package edu.ecnu.scsse.pizza.consumer.server.exception;

public class ConsumerServerException extends Exception {

    private ExceptionType exceptionType;

    protected String hintMessage = "服务器忙，请稍后重试。";

    public ConsumerServerException(ExceptionType exceptionType,  String hintMessage, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public ConsumerServerException(ExceptionType exceptionType, String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public ConsumerServerException(ExceptionType exceptionType, String hintMessage, String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = exceptionType;
        this.hintMessage = hintMessage;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getHintMessage() {
        return hintMessage;
    }

    public void setHintMessage(String hintMessage) {
        this.hintMessage = hintMessage;
    }
}
