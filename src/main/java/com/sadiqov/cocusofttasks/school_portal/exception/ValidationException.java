package com.sadiqov.cocusofttasks.school_portal.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {

    private Map<String, Object> details;

    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String message, Map<String, Object> details) {
        super(message);
        this.details = details;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
