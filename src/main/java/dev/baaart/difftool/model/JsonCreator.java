package dev.baaart.difftool.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonCreator {


    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveFile(String name, String contents, Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.err.println("Failed to create directory!" + e.getMessage());
            }
        }

        var savePath = Paths.get(path.toString() + '/' + name);

        try (BufferedWriter writer = Files.newBufferedWriter(savePath, StandardCharsets.UTF_8)) {

            writer.write(prettifyJson(contents));

        } catch (IOException e) {
            System.err.println("Failed to json file directory!" + e.getMessage());
        }
    }

    private static String prettifyJson(String json) throws JsonProcessingException {
        Object jsonObject = mapper.readValue(json, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }

}
