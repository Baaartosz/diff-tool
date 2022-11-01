package dev.baaart.difftool.model;

import dev.baaart.difftool.ANSI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class DiffFileGenerator {

    private static final Pattern expectedPattern = Pattern.compile("(?s)(?<=expected:<).*?(?=> but)");
    private static final Pattern actualPattern = Pattern.compile("(?s)(?<=was:<).*?(?=> within)");

    public static void generate(String folderName) {
        try {
            Path outputDir = assignOutputFolder(folderName);
            var clipboardText = ClipboardGrabber.INSTANCE.getContents();

            OutputExtractor outputExtractor = new OutputExtractor(expectedPattern, actualPattern);
            var outputPair = outputExtractor.extract(clipboardText);

            JsonCreator.saveFile("expected.json", outputPair.getLeft(), outputDir);
            JsonCreator.saveFile("actual.json", outputPair.getRight(), outputDir);

            System.out.println(ANSI.GREEN + "Successfully generated diff files.");
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Generation Failure: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static Path assignOutputFolder(String folderName) throws Exception {
        var scratchDirectory = ConfigManager.getInstance().get().getProperty("scratchFolder");

        if (scratchDirectory == null) {
            System.out.println(ANSI.BOLD + ANSI.RED + "Scratch folder directory has not been set!" + ANSI.RESET);
            System.out.println(ANSI.GREEN + "run: diff config -o='<scratchFolderPath>'");
            throw new Exception("Scratch folder is unassigned.");
        }

        if (!folderName.equals("")) {
            scratchDirectory += '/' + folderName + '/';
        }

        return Paths.get(scratchDirectory);
    }


}
