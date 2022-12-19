package by.bsu.advertisement.service.exception.device;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException() {}

    public DeviceNotFoundException(String message) {
        super(message);
    }

    public DeviceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceNotFoundException(Throwable cause) {
        super(cause);
    }
}
