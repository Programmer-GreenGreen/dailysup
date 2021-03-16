package project.dailysup.device.exception;

import project.dailysup.common.exception.BadRequestException;

public class DeviceNotFoundException extends BadRequestException {

    public DeviceNotFoundException(String message) {
        super(message);
    }

    public DeviceNotFoundException() {
        super("Invalid Fcm Token");
    }
}
