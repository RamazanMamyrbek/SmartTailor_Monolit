package org.ramazanmamyrbek.smarttailor.exception;

public class InvalidVerificationCodeException extends RuntimeException{
    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
