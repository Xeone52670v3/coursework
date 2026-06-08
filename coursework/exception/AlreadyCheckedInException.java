package com.worktime.exception;

public class AlreadyCheckedInException extends RuntimeException {
    public AlreadyCheckedInException(String msg) { super(msg); }
}
