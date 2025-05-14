package com.free.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDnaSequenceServiceTest {

    private DefaultDnaSequenceService service;

    @BeforeEach
    void setUp() {
        service = new DefaultDnaSequenceService();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ATCG", "AAAA", "GGGG", "CCCC", "TTTT", "ATGCATGC"})
    void isValidSequence_shouldReturnTrueForValidSequences(String sequence) {
        boolean result = service.isValidSequence(sequence);
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "ATBG", "atcg", "1234", "XYZP"})
    void isValidSequence_shouldReturnFalseForInvalidSequences(String sequence) {
        boolean result = service.isValidSequence(sequence);
        assertThat(result).isFalse();
    }

    @Test
    void isValidSequence_shouldReturnFalseForNullInput() {
        boolean result = service.isValidSequence(null);
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "ATCG, 50.0",
            "GGGG, 100.0",
            "AAAA, 0.0",
            "ATAT, 0.0",
            "ACGTACGT, 50.0",
            "GGCC, 100.0",
            "ATC, 33.3"
    })
    void calculateGcContent_shouldReturnCorrectPercentage(String sequence, double expected) {
        double result = service.calculateGcContent(sequence);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findMotifIndices_shouldReturnEmptyListForNullSequence() {
        List<Integer> result = service.findMotifIndices(null, "AT");
        assertThat(result).isEmpty();
    }

    @Test
    void findMotifIndices_shouldReturnEmptyListForNullMotif() {
        List<Integer> result = service.findMotifIndices("ATCG", null);
        assertThat(result).isEmpty();
    }

    @Test
    void findMotifIndices_shouldReturnEmptyListForEmptyMotif() {
        List<Integer> result = service.findMotifIndices("ATCG", "");
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "ATCGATCGAT, AT, 0",
            "ATCGATCGAT, AT, 4",
            "ATCGATCGAT, AT, 8",
            "GGGGGGGG, GG, 0",
            "GGGGGGGG, GG, 1",
            "GGGGGGGG, GG, 2",
            "GGGGGGGG, GG, 3",
            "GGGGGGGG, GG, 4",
            "GGGGGGGG, GG, 5",
            "GGGGGGGG, GG, 6"
    })
    void findMotifIndices_shouldContainExpectedIndex(String sequence, String motif, int expectedIndex) {
        List<Integer> result = service.findMotifIndices(sequence, motif);
        assertThat(result).contains(expectedIndex);
    }

    @Test
    void findMotifIndices_shouldNotFindNonExistingMotif() {
        List<Integer> result = service.findMotifIndices("ATCG", "TG");
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "ATCG, AUCG",
            "TTTT, UUUU",
            "ATGCATGC, AUGCAUGC"
    })
    void transcribeToRna_shouldReplaceTwithU(String dna, String expectedRna) {
        String result = service.transcribeToRna(dna);
        assertThat(result).isEqualTo(expectedRna);
    }

    @ParameterizedTest
    @CsvSource({
            "ATCG, TAGC",
            "AAAA, TTTT",
            "ACGT, TGCA",
            "TTAA, AATT"
    })
    void getComplementarySequence_shouldReturnCorrectSequence(String sequence, String expected) {
        String result = service.getComplementarySequence(sequence);
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "ATCG, CGAT",
            "AAAA, TTTT",
            "ACGT, ACGT",
            "TTAA, TTAA"
    })
    void getReverseComplementarySequence_shouldReturnCorrectSequence(String sequence, String expected) {
        String result = service.getReverseComplementarySequence(sequence);
        assertThat(result).isEqualTo(expected);
    }
}