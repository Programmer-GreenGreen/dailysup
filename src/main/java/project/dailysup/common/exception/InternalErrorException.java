package project.dailysup.common.exception;

public class InternalErrorException extends RuntimeException{
    public InternalErrorException() {
        super();
    }

    public InternalErrorException(String message) {
        super(message);
    }
}
