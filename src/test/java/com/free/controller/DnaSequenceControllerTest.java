package com.free.controller;

import com.free.service.DnaSequenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DnaSequenceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DnaSequenceService dnaSequenceService;

    @InjectMocks
    private DnaSequenceController dnaSequenceController;

    @BeforeEach
    void setUp() {
        // Initialize MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(dnaSequenceController)
                .build();
    }

    @Test
    void validateSequence_shouldReturnTrueForValidDna() throws Exception {
        String validDna = "ATCG";
        when(dnaSequenceService.isValidSequence(validDna)).thenReturn(true);

        mockMvc.perform(get("/api/sequence/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sequence\":\"ATCG\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));

        verify(dnaSequenceService).isValidSequence(validDna);
    }

    @Test
    void calculateGcContent_shouldReturnCorrectValue() throws Exception {
        String sequence = "ATCG";
        double expectedContent = 50.0;
        when(dnaSequenceService.calculateGcContent(sequence)).thenReturn(expectedContent);

        mockMvc.perform(get("/api/sequence/gc-content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sequence\":\"ATCG\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gc_content").value(expectedContent));
    }

    @Test
    void findMotifIndices_shouldReturnCorrectPositions() throws Exception {
        String sequence = "ATCGATCGAT";
        String motif = "AT";
        List<Integer> expectedIndices = List.of(0, 4, 8);
        when(dnaSequenceService.findMotifIndices(sequence, motif)).thenReturn(expectedIndices);

        mockMvc.perform(get("/api/sequence/find-motif")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sequence\":\"ATCGATCGAT\", \"motif\":\"AT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motif_indices[0]").value(0))
                .andExpect(jsonPath("$.motif_indices[1]").value(4))
                .andExpect(jsonPath("$.motif_indices[2]").value(8));
    }

    @Test
    void transcribeToRna_shouldConvertTtoU() throws Exception {
        String dna = "ATCG";
        String expectedRna = "AUCG";
        when(dnaSequenceService.transcribeToRna(dna)).thenReturn(expectedRna);

        mockMvc.perform(get("/api/sequence/transcribe-to-rna")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sequence\":\"ATCG\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rna_sequence").value(expectedRna));
    }

    @Test
    void getComplementarySequence_shouldReturnCorrectSequence() throws Exception {
        String dna = "ATCG";
        String expectedComplement = "CGAT";
        String expectedReverseComplement = "TAGC";
        when(dnaSequenceService.getComplementarySequence(dna)).thenReturn(expectedComplement);
        when(dnaSequenceService.getReverseComplementarySequence(dna)).thenReturn(expectedReverseComplement);

        mockMvc.perform(get("/api/sequence/complement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sequence\":\"ATCG\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.complementary_sequence").value(expectedComplement))
                .andExpect(jsonPath("$.reverse_complementary_sequence").value(expectedReverseComplement));
    }

    @Test
    void invalidJson_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/sequence/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(dnaSequenceService);
    }
}