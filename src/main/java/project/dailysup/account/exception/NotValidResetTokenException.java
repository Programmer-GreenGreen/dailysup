package project.dailysup.account.exception;

import project.dailysup.common.exception.BadRequestException;

public class NotValidResetTokenException extends BadRequestException {
    public NotValidResetTokenException(String message) {
        super(message);
    }

    public NotValidResetTokenException() {
        super("유효하지 않은 인증 코드입니다.");
    }
}
