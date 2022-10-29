package dev.baaart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class OutputExtractorTest {

    ObjectMapper mapper = new ObjectMapper();

    final String validOutput = """
            Some error because a test scenario failed: expected: <
            {
                "embedded" : true,
                "audioType" : "Stereo",
                "audioTrackMap" : [
                {
                    "trackNumber" : 1,
                    "trackAssignment" : "L"
                },
                {
                    "trackNumber" : 2,
                    "trackAssignment" : "R"
                }
                ]
            }> but was: <
            {
                "embedded" : true,
                "audioType" : "Dolby 5.1",
                "audioTrackMap" : [
                {
                    "trackNumber" : 1,
                    "trackAssignment" : "L"
                },
                {
                    "trackNumber" : 2,
                    "trackAssignment" : "R"
                }
                ]
            }> within 1 minute.
            """;

    final String invalidOutput = """
            Some error because a test scenario failed:
            {
                "embedded" : true,
                "audioType" : "Stereo",
                "audioTrackMap" : [
                {
                    "trackNumber" : 1,
                    "trackAssignment" : "L"
                },
                {
                    "trackNumber" : 2,
                    "trackAssignment" : "R"
                }
                ]
            }
            but was: <
            {
                "embedded" : true,
                "audioType" : "Dolby 5.1",
                "audioTrackMap" : [ ]
            }> within 1 minute.
            """;

    final String validExpected = """
            {
                "embedded" : true,
                "audioType" : "Stereo",
                "audioTrackMap" : [
                {
                    "trackNumber" : 1,
                    "trackAssignment" : "L"
                },
                {
                    "trackNumber" : 2,
                    "trackAssignment" : "R"
                }
                ]
            }
            """;

    final String validActual = """
            {
                "embedded" : true,
                "audioType" : "Dolby 5.1",
                "audioTrackMap" : [
                {
                    "trackNumber" : 1,
                    "trackAssignment" : "L"
                },
                {
                    "trackNumber" : 2,
                    "trackAssignment" : "R"
                }
                ]
            }
            """;

    Pattern expectedPattern = Pattern.compile("(?s)(?<=expected: <).*?(?=> but)");
    Pattern actualPattern = Pattern.compile("(?s)(?<=was: <).*?(?=> within)");

    @SneakyThrows
    @Test
    void shouldExtractCorrectElementsFromOutput() {
        Extractor extractor = new OutputExtractor(expectedPattern,actualPattern);
        Pair<String, String> results = extractor.extract(validOutput);

        assertNotNull(results);
        assertEquals(prettifyJson(results.getLeft()), prettifyJson(validExpected));
        assertEquals(prettifyJson(results.getRight()),  prettifyJson(validActual));
    }

    @SneakyThrows
    @Test
    void shouldThrowErrorWhenUnableToFindMatch() {
        Extractor extractor = new OutputExtractor(expectedPattern,actualPattern);

        Throwable exception = assertThrows(Exception.class, () -> extractor.extract(invalidOutput));
        assertEquals("Unable to find match using \"(?s)(?<=expected: <).*?(?=> but)\"", exception.getMessage());
    }

    @SneakyThrows
    @Test()
    void shouldThrowErrorOnEmptyFileInput() {
        Extractor extractor = new OutputExtractor(expectedPattern,actualPattern);

        Throwable exception = assertThrows(InvalidInputException.class, () -> extractor.extract(""));
        assertEquals("Output file is empty or missing.", exception.getMessage());
    }

    @SneakyThrows
    @Test()
    void shouldThrowErrorOnNullInput() {
        Extractor extractor = new OutputExtractor(expectedPattern,actualPattern);

        Throwable exception = assertThrows(InvalidInputException.class, () -> extractor.extract(""));
        assertEquals("Output file is empty or missing.", exception.getMessage());
    }

    private String prettifyJson(String json) throws JsonProcessingException {
        Object jsonObject = mapper.readValue(json, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}