package dev.baaart;

import dev.baaart.commands.CleanupCommand;
import dev.baaart.commands.ConfigCommand;
import dev.baaart.commands.GenerateCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.ParentCommand;


@Command(
        name = "diff",
        header = """
                \u001B[31m )\\ )         (      (    \s
                \u001B[31m(()/(    (    )\\ )   )\\ ) \s
                \u001B[31m /(_))   )\\  (()/(  (()/( \s
                \u001B[33m(_))_   ((_)  /(_))  /(_))\s
                \u001B[35m |   \\   (_) (_) _| (_) _|\s
                \u001B[35m | |) |  | |  |  _|  |  _|\s
                \u001B[35m |___/   |_|  |_|    |_|  Speeding up debugging.
                \u001B[0m""",
        mixinStandardHelpOptions = true,
        version = "diff-tool 0.1",
        description = "Generates diff files for inspection within intellij from error outputs",
        subcommands = {
                GenerateCommand.class,
                ConfigCommand.class,
                CleanupCommand.class
        }
)
public class DiffTool implements Callable<Integer> {

    @ParentCommand
    private DiffTool diffTool;

    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new DiffTool());
        int returnCode = cli.execute(args);
        System.exit(returnCode);
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
