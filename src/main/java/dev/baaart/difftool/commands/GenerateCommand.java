package dev.baaart.difftool.commands;

import dev.baaart.difftool.ANSI;
import dev.baaart.difftool.model.ClipboardGrabber;
import dev.baaart.difftool.model.ConfigManager;
import dev.baaart.difftool.model.JsonCreator;
import dev.baaart.difftool.model.OutputExtractor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import static picocli.CommandLine.*;

@Command(
        name = "generate", mixinStandardHelpOptions = true,
        aliases = {"gen", "g"},
        description = "Runs diff tool generation comparing output log file for specific tags to find expected and actual results.\n"
)
public class GenerateCommand implements Callable<Integer> {

    @Parameters(
            index = "0", arity = "0..1", paramLabel = "<folderName>",
            description = "Creates a new folder with the desired name to insert the files into otherwise defaults to base folder"
    )
    private String folderName = "";

    @Option(
            names = {"-h", "--help", "-?", "-help"}, usageHelp = true,
            description = "Display this help and exit"
    )
    private boolean help = false;


    @Override
    public Integer call() throws Exception {
        var scratchDirectory = ConfigManager.getInstance().get().getProperty("scratchFolder");
        if (scratchDirectory == null) {
            System.out.println(ANSI.BOLD + ANSI.RED + "Scratch folder directory has not been set!" + ANSI.RESET);
            System.out.println(ANSI.GREEN + "run: diff config -o=<scratchFolderPath>");
            return -1;
        }

        if (!folderName.equals("")) {
            scratchDirectory += '/' + folderName + '/';
        }
        Path outputDirectory = Paths.get(scratchDirectory);

        var clipboardText = ClipboardGrabber.INSTANCE.getContents();

        Pattern expectedPattern = Pattern.compile("(?s)(?<=expected:<).*?(?=> but)");
        Pattern actualPattern = Pattern.compile("(?s)(?<=was:<).*?(?=> within)");
        OutputExtractor outputExtractor = new OutputExtractor(expectedPattern, actualPattern);

        var outputPair = outputExtractor.extract(clipboardText);

        JsonCreator.saveFile("expected.json", outputPair.getLeft(), outputDirectory);
        JsonCreator.saveFile("actual.json", outputPair.getRight(), outputDirectory);

        return 0;
    }


}
