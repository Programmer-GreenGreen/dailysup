package project.dailysup.common.exception;

public class FailedFileSaveException extends RuntimeException {
    public FailedFileSaveException() {
        super();
    }

    public FailedFileSaveException(String message) {
        super(message);
    }
}
