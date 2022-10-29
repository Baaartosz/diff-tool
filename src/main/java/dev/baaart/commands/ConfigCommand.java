package dev.baaart.commands;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(
        name = "config",
        description = "Configure diff tool properties for finer tuning\n"
)
public class ConfigCommand implements Callable<Integer> {

    @Option(
            names = {"-o", "-output"},
            description = "Set to your preferred scratch folder path"
    )
    private String scratchFolderPath = "";

    @Option(
            names = {"-h", "--help", "-?", "-help"}, usageHelp = true,
            description = "Display this help and exit"
    )
    private boolean help;

    @Override
    public Integer call() throws Exception {
        System.out.println(scratchFolderPath);
        return 0;
    }
}
