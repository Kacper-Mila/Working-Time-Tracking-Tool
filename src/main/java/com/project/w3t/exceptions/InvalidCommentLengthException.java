package com.project.w3t.exceptions;

public class InvalidCommentLengthException extends Exception {
    public InvalidCommentLengthException() {
        super("Your comment is invalid!");
    }
}
