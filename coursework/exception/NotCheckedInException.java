package com.worktime.exception;

public class NotCheckedInException extends RuntimeException {
    public NotCheckedInException(String msg) { super(msg); }
}
