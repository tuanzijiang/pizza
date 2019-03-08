package edu.ecnu.scsse.pizza.bussiness.server.exception;

public class BusinessServerException extends Exception {

    private ExceptionType exceptionType;

    protected String hintMessage = "服务器忙，请稍后重试。";

    public BusinessServerException(ExceptionType exceptionType, String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public BusinessServerException(ExceptionType exceptionType, String hintMessage, String message, Throwable cause) {
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