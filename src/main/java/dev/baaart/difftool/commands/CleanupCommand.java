package dev.baaart.difftool.commands;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(
        name = "cleanup",
        aliases = {"c"},
        description = "Cleans up diff files inside scratch folder\n"
)
public class CleanupCommand implements Callable<Integer> {

    @Option(
            names = {"-o", "-old"},
            description = "Clear all files inside base diff scratch folder"
    )
    private boolean removeOldFilesCreatedInBaseFolder = false;

    @Option(
            names = {"-a", "-all"},
            description = "Clear all files inside base diff scratch folder"
    )
    private boolean removeAllFilesCreatedInBaseFolder = false;

    @Option(
            names = {"-h", "--help", "-?", "-help"}, usageHelp = true,
            description = "Display this help and exit"
    )
    private boolean help;

    @Override
    public Integer call() throws Exception {
        System.out.println("removeOldFilesCreatedInBaseFolder = " + removeOldFilesCreatedInBaseFolder);
        System.out.println("removeAllFilesCreatedInBaseFolder = " + removeAllFilesCreatedInBaseFolder);
        return null;
    }
}
