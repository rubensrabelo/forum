package io.github.rubensrabelo.project.forum.shared.middlewares.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.rubensrabelo.project.forum.shared.exceptions.DatabaseException;
import io.github.rubensrabelo.project.forum.shared.exceptions.InvalidCredentialsException;
import io.github.rubensrabelo.project.forum.shared.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.project.forum.shared.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<StandardError> resourceNotFound(
                        ResourceNotFoundException e,
                        HttpServletRequest request) {

                String error = "Resource not found";
                HttpStatus status = HttpStatus.NOT_FOUND;

                StandardError err = new StandardError(
                                Instant.now(),
                                status.value(),
                                error,
                                e.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(DatabaseException.class)
        public ResponseEntity<StandardError> database(
                        DatabaseException e,
                        HttpServletRequest request) {

                String error = "Database error";
                HttpStatus status = HttpStatus.BAD_REQUEST;

                StandardError err = new StandardError(
                                Instant.now(),
                                status.value(),
                                error,
                                e.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<StandardError> invalidCredentials(
                        InvalidCredentialsException e,
                        HttpServletRequest request) {

                String error = "Invalid credentials";
                HttpStatus status = HttpStatus.UNAUTHORIZED;

                StandardError err = new StandardError(
                                Instant.now(),
                                status.value(),
                                error,
                                e.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<StandardError> userAlreadyExists(
                        UserAlreadyExistsException e,
                        HttpServletRequest request) {

                String error = "User already exists";
                HttpStatus status = HttpStatus.CONFLICT;

                StandardError err = new StandardError(
                                Instant.now(),
                                status.value(),
                                error,
                                e.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<StandardError> validation(
                        MethodArgumentNotValidException e,
                        HttpServletRequest request) {

                String error = "Validation error";
                HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

                ValidationError err = new ValidationError(
                                Instant.now(),
                                status.value(),
                                error,
                                "Invalid fields",
                                request.getRequestURI());

                e.getBindingResult().getFieldErrors()
                                .forEach(fieldError -> err.addError(fieldError.getField(),
                                                fieldError.getDefaultMessage()));

                return ResponseEntity.status(status).body(err);
        }
}