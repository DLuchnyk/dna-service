package com.free.service;

import java.util.List;

public interface DnaSequenceService {
    boolean isValidSequence(String sequence);

    double calculateGcContent(String sequence);

    List<Integer> findMotifIndices(String sequence, String motif);

    String transcribeToRna(String sequence);

    String getComplementarySequence(String sequence);

    String getReverseComplementarySequence(String sequence);
}
