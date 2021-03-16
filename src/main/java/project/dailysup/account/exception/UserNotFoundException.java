package project.dailysup.account.exception;

import project.dailysup.common.exception.BadRequestException;

public class UserNotFoundException extends BadRequestException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("Do Not Exist Current Account");
    }
}
