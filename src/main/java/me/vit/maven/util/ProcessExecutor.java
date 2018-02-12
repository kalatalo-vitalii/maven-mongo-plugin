package me.vit.maven.util;

import me.vit.maven.exception.execute.ProcessExecutorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

public class ProcessExecutor {

    public String executeProcess(String command) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);

        } catch (IOException e) {
            String message = String.format("error while executing command: %s", command);
            throw new ProcessExecutorException(message, e);
        }
        return getProcessOutput(process);
    }

    public String executeProcess(List<String> commands) {
        Process process;
        try {
            process = new ProcessBuilder(commands)
                    .redirectErrorStream(true)
                    .start();

        } catch (IOException e) {
            String message = String.format("error while executing command: %s", commands);
            throw new ProcessExecutorException(message, e);
        }
        return getProcessOutput(process);
    }

    private String getProcessOutput(Process process) {
        ProcessOutputReader reader = new ProcessOutputReader(process.getInputStream());
        reader.start();
        try {
            process.waitFor();
            reader.join();

        } catch (InterruptedException e) {
            throw new ProcessExecutorException("error while waiting command execution", e);

        } finally {
            process.destroy();
        }

        return reader.getResult();
    }

    private class ProcessOutputReader extends Thread {
        private InputStream is;
        private StringWriter sw = new StringWriter();

        ProcessOutputReader(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            try {
                int c;
                while ((c = is.read()) != -1)
                    sw.write(c);

            } catch (Exception e) {
                String message = "error while obtaining command output";
                throw new ProcessExecutorException(message, e);
            }
        }

        String getResult() {
            return sw.toString();
        }
    }
}
