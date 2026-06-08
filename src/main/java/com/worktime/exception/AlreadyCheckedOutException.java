package com.worktime.exception;

public class AlreadyCheckedOutException extends RuntimeException {
    public AlreadyCheckedOutException(String msg) { super(msg); }
}
