package br.com.pbcompass.demoparkapi.exception;

public class ParkingSpaceUniqueCodeViolationException extends RuntimeException {

    public ParkingSpaceUniqueCodeViolationException(String message) {
        super(message);
    }
}
