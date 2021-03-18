package project.dailysup.item.exception;

import project.dailysup.common.exception.BadRequestException;

public class ItemNotFoundException extends BadRequestException {
    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException() {
        super("Item Not Found Exception");
    }
}
