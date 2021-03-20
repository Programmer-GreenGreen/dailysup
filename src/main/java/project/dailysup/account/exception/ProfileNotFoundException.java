package project.dailysup.account.exception;

import project.dailysup.common.exception.BadRequestException;

public class ProfileNotFoundException extends BadRequestException {
    public ProfileNotFoundException() {
        super("Profile Picture Not Found");
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
