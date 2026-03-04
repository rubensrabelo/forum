package io.github.rubensrabelo.project.forum.shared.middlewares.handler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldErrorMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldErrorMessage> getErrors() {
        return errors;
    }

    public void addError(String field, String message) {
        errors.add(new FieldErrorMessage(field, message));
    }
}