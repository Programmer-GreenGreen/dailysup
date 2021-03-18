package project.dailysup.account.exception;

import project.dailysup.common.exception.BadRequestException;

public class NotValidWithdrawRequest  extends BadRequestException {
    public NotValidWithdrawRequest(String message) {
        super(message);
    }

    public NotValidWithdrawRequest() {
        super("Failed Withdraw Validation");
    }
}
