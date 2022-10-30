package dev.baaart.difftool.commands;

import java.util.concurrent.Callable;

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
        System.out.println(folderName);
        return 0;
    }


}
