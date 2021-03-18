package project.dailysup.account.exception;

import project.dailysup.common.exception.BadRequestException;

public class DuplicatedAccountException extends BadRequestException {
    public DuplicatedAccountException(String message) {
        super(message);
    }

    public DuplicatedAccountException(){
        super("Duplicated Account");
    }
}
