package org.soumyadev.crowdfundinnovativeworld.ExceptionHandling;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String user_credential_is_not_valid) {
    }
}
