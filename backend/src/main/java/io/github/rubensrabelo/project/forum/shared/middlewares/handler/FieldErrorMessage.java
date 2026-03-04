package io.github.rubensrabelo.project.forum.shared.middlewares.handler;

public class FieldErrorMessage {

    private String field;
    private String message;

    public FieldErrorMessage(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}