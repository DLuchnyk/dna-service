package com.free.controller;

import com.free.dto.request.DnaSequenceRequest;
import com.free.dto.request.MotifSearchRequest;
import com.free.dto.response.ComplementResultDto;
import com.free.dto.response.GcContentDto;
import com.free.dto.response.MotifIndicesDto;
import com.free.dto.response.RnaSequenceDto;
import com.free.dto.response.ValidationResultDto;
import com.free.service.DnaSequenceService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sequence")
@Validated
public class DnaSequenceController {

    @Resource
    private DnaSequenceService dnaSequenceService;

    @GetMapping("/validate")
    public ValidationResultDto validateSequence(@RequestBody DnaSequenceRequest request) {
        return ValidationResultDto.of(
                dnaSequenceService.isValidSequence(request.sequence())
        );
    }

    @GetMapping("/gc-content")
    public GcContentDto calculateGcContent(@RequestBody @Valid DnaSequenceRequest request) {
        return GcContentDto.of(
                dnaSequenceService.calculateGcContent(request.sequence())
        );
    }

    @GetMapping("/find-motif")
    public MotifIndicesDto findMotifIndices(@RequestBody @Valid MotifSearchRequest request) {
        return MotifIndicesDto.of(
                dnaSequenceService.findMotifIndices(request.sequence(), request.motif())
        );
    }

    @GetMapping("/transcribe-to-rna")
    public RnaSequenceDto transcribeToRna(@RequestBody @Valid DnaSequenceRequest request) {
        return RnaSequenceDto.of(
                dnaSequenceService.transcribeToRna(request.sequence())
        );
    }

    @GetMapping("/complement")
    public ComplementResultDto getComplement(@RequestBody @Valid DnaSequenceRequest request) {
        return ComplementResultDto.of(
                dnaSequenceService.getComplementarySequence(request.sequence()),
                dnaSequenceService.getReverseComplementarySequence(request.sequence())
        );
    }
}
