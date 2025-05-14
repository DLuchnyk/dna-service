package com.free.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GcContentDto(
        @JsonProperty("gc_content")
        double gcContent
) {
    public static GcContentDto of(double gcContent) {
        return new GcContentDto(gcContent);
    }
}
