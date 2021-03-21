package project.dailysup.file;

import project.dailysup.common.exception.BadRequestException;

public class ImageNotFoundException extends BadRequestException {
    public ImageNotFoundException() {
        super("Item Picture Not Found");
    }

    public ImageNotFoundException(String message) {
        super(message);
    }
}
