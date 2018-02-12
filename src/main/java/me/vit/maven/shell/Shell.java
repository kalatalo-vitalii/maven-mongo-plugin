package me.vit.maven.shell;

import me.vit.maven.exception.execute.ProcessExecutorException;
import me.vit.maven.exception.execute.ShellException;
import me.vit.maven.shell.options.Authentication;
import me.vit.maven.shell.options.ShellOptions;
import me.vit.maven.util.ProcessExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Shell {
    private static final Logger LOGGER = LoggerFactory.getLogger(Shell.class);
    private ProcessExecutor executor;
    private String shellPath;
    private ShellOptions shellOptions;

    public Shell(String shellPath, ShellOptions shellOptions) {
        this.shellPath = shellPath;
        this.shellOptions = shellOptions;
        this.executor = new ProcessExecutor();
    }

    public String version() {
        String cmd = String.format("%s --quiet --nodb --version", shellPath);
        return executeCommand(cmd);
    }

    public String eval(String expression) {
        if (shellOptions == null) {
            throw new ShellException("'shellOptions' required to execute 'eval' cmd");
        }
        List<String> commands = buildBaseCommands();
        commands.addAll(buildEvalOptions(expression));

        return executeCommands(commands);
    }

    public void executeJs(File file) {
        if (shellOptions == null) {
            throw new ShellException("'shellOptions' required to execute .js script");
        }
        List<String> commands = buildBaseCommands();
        commands.add(getFilePath(file));

        executeCommands(commands);
    }

    private List<String> buildBaseCommands() {
        List<String> commands = new ArrayList<>();
        commands.add(shellPath);
        commands.add("--quiet");
        commands.addAll(buildConnectionOptions(shellOptions));
        commands.addAll(buildAuthenticationOptions(shellOptions.getAuthentication()));
        return commands;
    }

    private static List<String> buildConnectionOptions(ShellOptions shellOptions) {
        return Arrays.asList(
                "--host", shellOptions.getHost(),
                "--port", shellOptions.getPort(),
                shellOptions.getDb()
        );
    }

    private static List<String> buildAuthenticationOptions(Authentication auth) {
        if (auth == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(
                "--username", auth.getUser(),
                "--password", auth.getPassword(),
                "--authenticationDatabase", auth.getDb()
        );
    }

    private static List<String> buildEvalOptions(String expression) {
        return Arrays.asList(
                "--eval", expression
        );
    }

    private String executeCommand(String command) {
        LOGGER.debug("    └ command: {}", command);
        try {
            String output = executor.executeProcess(command);
            LOGGER.debug("    └ output:\n{}", output);
            return output;

        } catch (ProcessExecutorException e) {
            throw new ShellException(String.format("error while executing cmd: %s", command), e);
        }
    }

    private String executeCommands(List<String> commands) {
        LOGGER.debug("    └ commands: {}", commands);
        try {
            String output = executor.executeProcess(commands);
            LOGGER.debug("    └ output:\n{}", output);
            return output;

        } catch (ProcessExecutorException e) {
            throw new ShellException(String.format("error while executing cmd: %s", commands), e);
        }
    }

    private static String getFilePath(File file) {
        try {
            return file.getCanonicalPath();

        } catch (IOException e) {
            throw new ShellException(String.format("can't get file path: %s", e.getMessage()), e);
        }
    }
}
