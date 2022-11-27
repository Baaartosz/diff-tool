package dev.baaart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.baaart.difftool.model.Extractor;
import dev.baaart.difftool.model.InvalidInputException;
import dev.baaart.difftool.model.OutputExtractor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class OutputExtractorTest {

    ObjectMapper mapper = new ObjectMapper();

    final String validOutput = "Some error because a test scenario failed: expected: <\n" + "\n" + "This is the bearer code\n" + "\n" + "{\n" + "    \"embedded\" : true,\n" + "    \"audioType\" : \"Stereo\",\n" + "    \"audioTrackMap\" : [\n" + "    {\n" + "        \"trackNumber\" : 1,\n" + "        \"trackAssignment\" : \"L\"\n" + "    },\n" + "    {\n" + "        \"trackNumber\" : 2,\n" + "        \"trackAssignment\" : \"R\"\n" + "    }\n" + "    ]\n" + "}> but was: <\n" + "\n" + "This is the bearer code.\n" + "\n" + "{\n" + "    \"embedded\" : true,\n" + "    \"audioType\" : \"Dolby 5.1\",\n" + "    \"audioTrackMap\" : [\n" + "    {\n" + "        \"trackNumber\" : 1,\n" + "        \"trackAssignment\" : \"L\"\n" + "    },\n" + "    {\n" + "        \"trackNumber\" : 2,\n" + "        \"trackAssignment\" : \"R\"\n" + "    }\n" + "    ]\n" + "}> within 1 minute.\n";

    final String invalidOutput = "Some error because a test scenario failed:\n" + "{\n" + "    \"embedded\" : true,\n" + "    \"audioType\" : \"Stereo\",\n" + "    \"audioTrackMap\" : [\n" + "    {\n" + "        \"trackNumber\" : 1,\n" + "        \"trackAssignment\" : \"L\"\n" + "    },\n" + "    {\n" + "        \"trackNumber\" : 2,\n" + "        \"trackAssignment\" : \"R\"\n" + "    }\n" + "    ]\n" + "}\n" + "but was: <\n" + "{\n" + "    \"embedded\" : true,\n" + "    \"audioType\" : \"Dolby 5.1\",\n" + "    \"audioTrackMap\" : [ ]\n" + "}> within 1 minute.\n";

    final String validExpected = "{\n" + "    \"embedded\" : true,\n" + "    \"audioType\" : \"Stereo\",\n" + "    \"audioTrackMap\" : [\n" + "    {\n" + "        \"trackNumber\" : 1,\n" + "        \"trackAssignment\" : \"L\"\n" + "    },\n" + "    {\n" + "        \"trackNumber\" : 2,\n" + "        \"trackAssignment\" : \"R\"\n" + "    }\n" + "    ]\n" + "}\n";

    final String validActual = "{\n" + "    \"embedded\" : true,\n" + "    \"audioType\" : \"Dolby 5.1\",\n" + "    \"audioTrackMap\" : [\n" + "    {\n" + "        \"trackNumber\" : 1,\n" + "        \"trackAssignment\" : \"L\"\n" + "    },\n" + "    {\n" + "        \"trackNumber\" : 2,\n" + "        \"trackAssignment\" : \"R\"\n" + "    }\n" + "    ]\n" + "}\n";

    Pattern expectedPattern = Pattern.compile("(?s)(?<=expected: <).*?(?=> but)");
    Pattern actualPattern = Pattern.compile("(?s)(?<=was: <).*?(?=> within)");

    @SneakyThrows
    @Test
    void shouldExtractCorrectElementsFromOutput() {
        Extractor extractor = new OutputExtractor(expectedPattern, actualPattern);
        Pair<String, String> results = extractor.extract(validOutput);

        assertNotNull(results);
        assertEquals(prettifyJson(results.getLeft()), prettifyJson(validExpected));
        assertEquals(prettifyJson(results.getRight()), prettifyJson(validActual));
    }

    @SneakyThrows
    @Test
    void shouldThrowErrorWhenUnableToFindMatch() {
        Extractor extractor = new OutputExtractor(expectedPattern, actualPattern);

        Throwable exception = assertThrows(Exception.class, () -> extractor.extract(invalidOutput));
        assertEquals("Unable to find match using \"(?s)(?<=expected: <).*?(?=> but)\"", exception.getMessage());
    }

    @SneakyThrows
    @Test()
    void shouldThrowErrorOnEmptyFileInput() {
        Extractor extractor = new OutputExtractor(expectedPattern, actualPattern);

        Throwable exception = assertThrows(InvalidInputException.class, () -> extractor.extract(""));
        assertEquals("Output file is empty or missing.", exception.getMessage());
    }

    @SneakyThrows
    @Test()
    void shouldThrowErrorOnNullInput() {
        Extractor extractor = new OutputExtractor(expectedPattern, actualPattern);

        Throwable exception = assertThrows(InvalidInputException.class, () -> extractor.extract(""));
        assertEquals("Output file is empty or missing.", exception.getMessage());
    }

    private String prettifyJson(String json) throws JsonProcessingException {
        Object jsonObject = mapper.readValue(json, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}