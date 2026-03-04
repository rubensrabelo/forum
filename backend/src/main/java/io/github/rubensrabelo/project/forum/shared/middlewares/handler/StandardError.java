package io.github.rubensrabelo.project.forum.shared.middlewares.handler;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
