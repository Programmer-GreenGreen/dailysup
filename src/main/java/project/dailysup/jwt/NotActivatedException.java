package project.dailysup.jwt;

import project.dailysup.common.exception.BadRequestException;

public class NotActivatedException extends BadRequestException {
    public NotActivatedException(String message) {
        super(message);
    }
}
