package com.free.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RnaSequenceDto(
        @JsonProperty("rna_sequence")
        String rnaSequence
) {
    public static RnaSequenceDto of(String rnaSequence) {
        return new RnaSequenceDto(rnaSequence);
    }
}