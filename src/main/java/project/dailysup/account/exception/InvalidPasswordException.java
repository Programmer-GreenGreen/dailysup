package project.dailysup.account.exception;

import project.dailysup.common.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException() {
        super("패스워드에 오류가 있습니다.");
    }
}
