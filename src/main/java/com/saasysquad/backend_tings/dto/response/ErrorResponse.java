package com.saasysquad.backend_tings.dto.response;

public record ErrorResponse(
        String errorCode,
        String errorMessage
) {}
