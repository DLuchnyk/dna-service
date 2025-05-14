package com.free.dto.response;

public record ValidationResultDto(boolean valid) {
    public static ValidationResultDto of(boolean valid) {
        return new ValidationResultDto(valid);
    }
}
