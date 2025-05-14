package com.free.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ComplementResultDto(
        @JsonProperty("complementary_sequence")
        String complementarySequence,
        @JsonProperty("reverse_complementary_sequence")
        String reverseComplementarySequence) {
    public static ComplementResultDto of(String complementarySequence, String reverseComplementarySequence) {
        return new ComplementResultDto(complementarySequence, reverseComplementarySequence);
    }
}
