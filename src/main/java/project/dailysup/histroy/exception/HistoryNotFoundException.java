package project.dailysup.histroy.exception;

import project.dailysup.common.exception.BadRequestException;

public class HistoryNotFoundException extends BadRequestException {
    public HistoryNotFoundException(String message) {
        super(message);
    }

    public HistoryNotFoundException() {
        super("Not found History");
    }
}
