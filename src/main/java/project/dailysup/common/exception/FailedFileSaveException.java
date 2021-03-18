package project.dailysup.common.exception;

public class FailedFileSaveException extends InternalErrorException {
    public FailedFileSaveException() {
        super();
    }

    public FailedFileSaveException(String message) {
        super(message);
    }
}
