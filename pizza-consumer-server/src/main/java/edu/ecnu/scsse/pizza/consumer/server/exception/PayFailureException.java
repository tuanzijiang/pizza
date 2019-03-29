package edu.ecnu.scsse.pizza.consumer.server.exception;

public class PayFailureException extends ConsumerServerException{

    private static final String message = "Exception occurs while calling alipay interfaces.";
    private static final String hintMessage = "支付失败。";

    public PayFailureException(String responseMsg) {
        super(ExceptionType.THIRD_PARTY, hintMessage, responseMsg);
    }

    public PayFailureException(Throwable cause) {
        super(ExceptionType.THIRD_PARTY, hintMessage, message, cause);
    }
}
