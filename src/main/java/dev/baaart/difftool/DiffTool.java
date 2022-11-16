package dev.baaart.difftool;

import dev.baaart.difftool.commands.ConfigCommand;
import dev.baaart.difftool.commands.GenerateCommand;
import dev.baaart.difftool.model.ConfigManager;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.*;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.ParentCommand;


@Command(
        name = "diff",
        header = """
               \u001B[33m\u001B[36mDiffTool v0.3\u001B[0m
               -- Made for Data Triggering!
               """,
        version = "diff-tool 0.3",
        description = """
                \nGenerates diff files for inspection within intellij from error outputs.
                \nCopy contents of cucumber output and run the generate command. Then two
                diff files will be placed in your scratch folder where you can compare them
                using the IDE
                """,
        optionListHeading = "Options:\n",
        subcommands = {
                GenerateCommand.class,
                ConfigCommand.class
        }
)
public class DiffTool implements Callable<Integer> {

    @ParentCommand
    private DiffTool diffTool;

    @Option(
            names = {"-h", "--help", "-?", "-help"}, usageHelp = true,
            description = "Display this help and exit"
    )
    private boolean help;

    public static void main(String[] args) {
        ConfigManager.getInstance();
        CommandLine cli = new CommandLine(new DiffTool());
        int returnCode = cli.execute(args);

        System.exit(returnCode);
    }

    @Override
    public Integer call() throws Exception {
        warnUser(ConfigManager.getInstance().isScratchDirMissing(), "Scratch directory is not set, run: difftool -output='<scratch directory>'");
        return 0;
    }

    private static void warnUser(boolean condition, String message){
        if(condition) System.out.println(ANSI.RED + ANSI.BOLD + "[WARNING] " + ANSI.RESET + message);
    }

}

