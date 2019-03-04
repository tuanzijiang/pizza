package edu.ecnu.scsse.pizza.consumer.server.model;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;

public abstract class Response {

    private ResultType resultType = ResultType.SUCCESS;

    private String errorMsg;

    private Throwable cause;

    public Response() {}

    public Response(ConsumerServerException e) {
        this.setResultType(ResultType.FAILURE);
        this.setErrorMsg(e.getHintMessage());
        this.setCause(e);
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
