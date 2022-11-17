package dev.baaart.difftool.commands;

import dev.baaart.difftool.ANSI;
import dev.baaart.difftool.model.ConfigManager;

import java.nio.file.Files;
import java.nio.file.Paths;
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
        if (!scratchFolderPath.equals("")) {
            if (!Files.exists(Paths.get(scratchFolderPath))) {
                System.out.println(ANSI.RED + ANSI.BOLD + "Cannot find path: '" + scratchFolderPath + "'" + ANSI.RESET);
                return -1;
            }

            var properties = ConfigManager.getInstance().get();
            properties.setProperty("scratchFolder", scratchFolderPath);

            ConfigManager.getInstance().save(properties);
            System.out.println(ANSI.GREEN + ANSI.BOLD + "Successfully set scratch folder path!" + ANSI.RESET);
        }
        return 0;
    }
}
