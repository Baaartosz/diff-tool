package dev.baaart.difftool.model;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputExtractor implements Extractor{

    private Pattern expectedPattern;
    private Pattern actualPattern;

    public OutputExtractor(Pattern expectedPattern, Pattern actualPattern) {
        this.expectedPattern = expectedPattern;
        this.actualPattern = actualPattern;
    }

    @Override
    public Pair<String, String> extract(String outputFile) throws Exception {
        if (Objects.equals(outputFile, "")) throw new InvalidInputException("Output file is empty or missing.");

        String expectedOutput = searchForMatching(expectedPattern, outputFile);
        String actualOutput = searchForMatching(actualPattern, outputFile);

        var trimmedExpectedMessage = expectedOutput.substring(expectedOutput.indexOf('{'));
        var trimmedActualMessage = actualOutput.substring(actualOutput.indexOf('{'));

        return new ImmutablePair<>(trimmedExpectedMessage, trimmedActualMessage);
    }

    private String searchForMatching(Pattern pattern, String string) throws Exception {
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()){
            return matcher.group(0);
        }
        throw new Exception("Unable to find match using \""+ pattern +"\"");
    }
}
