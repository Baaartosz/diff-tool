package dev.baaart;

import org.apache.commons.lang3.tuple.Pair;

public interface Extractor {
    Pair<String, String> extract(String outputFile) throws Exception;
}
