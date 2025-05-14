package com.free.service.impl;

import com.free.constant.Constants;
import com.free.service.DnaSequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DefaultDnaSequenceService implements DnaSequenceService {

    private static final char T_CHAR = 'T';
    private static final char U_CHAR = 'U';
    public static final char G_CHAR = 'G';
    public static final char C_CHAR = 'C';
    public static final char A_CHAR = 'A';
    private static final Map<Character, Character> CHARACTER_CHARACTER_MAP = Map.of(
            A_CHAR, T_CHAR,
            T_CHAR, A_CHAR,
            C_CHAR, G_CHAR,
            G_CHAR, C_CHAR
    );

    @Override
    public boolean isValidSequence(String sequence) {
        return StringUtils.isNoneEmpty(sequence) && sequence.matches(Constants.SEQUENCE_REGEX);
    }

    @Override
    public double calculateGcContent(String sequence) {
        long gcCount = sequence.chars()
                .filter(c -> c == G_CHAR || c == C_CHAR)
                .count();
        return Math.round((double) gcCount / sequence.length() * 100 * 10) / 10.0;
    }

    @Override
    public List<Integer> findMotifIndices(String sequence, String motif) {
        List<Integer> indices = new ArrayList<>();
        if (StringUtils.isEmpty(sequence) || StringUtils.isEmpty(motif)) {
            return indices;
        }

        int index = sequence.indexOf(motif);
        while (index >= 0) {
            indices.add(index);
            index = sequence.indexOf(motif, index + 1);
        }
        return indices;
    }

    @Override
    public String transcribeToRna(String sequence) {
        return sequence.replace(T_CHAR, U_CHAR);
    }

    @Override
    public String getComplementarySequence(String sequence) {
        return getComplementarySequenceInternal(sequence).toString();
    }

    @Override
    public String getReverseComplementarySequence(String sequence) {
        return getComplementarySequenceInternal(sequence).reverse().toString();
    }

    private StringBuilder getComplementarySequenceInternal(String sequence) {
        StringBuilder sb = new StringBuilder();
        for (char c : sequence.toCharArray()) {
            sb.append(CHARACTER_CHARACTER_MAP.get(c));
        }
        return sb;
    }
}
