package com.free.dto.request;

import com.free.constant.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DnaSequenceRequest(
        @NotBlank(message = Constants.SEQUENCE_CANNOT_BE_BLANK_ERROR_MESSAGE)
        @Pattern(regexp = Constants.SEQUENCE_REGEX, message = Constants.SEQUENCE_REGEX_ERROR_MESSAGE)
        String sequence) {
}
