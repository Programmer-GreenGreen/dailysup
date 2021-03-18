package project.dailysup.account.exception;

public class ProfileNotFoundException extends RuntimeException{
    public ProfileNotFoundException() {
        super();
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
