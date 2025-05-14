package com.free.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MotifIndicesDto(
        @JsonProperty("motif_indices")
        List<Integer> motifIndices
) {
    public static MotifIndicesDto of(List<Integer> motifIndices) {
        return new MotifIndicesDto(motifIndices);
    }
}
