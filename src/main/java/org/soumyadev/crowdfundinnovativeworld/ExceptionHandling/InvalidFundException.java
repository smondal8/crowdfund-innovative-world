package org.soumyadev.crowdfundinnovativeworld.ExceptionHandling;

public class InvalidFundException extends RuntimeException {
    public InvalidFundException(String s) {
        super(s);
    }
}
