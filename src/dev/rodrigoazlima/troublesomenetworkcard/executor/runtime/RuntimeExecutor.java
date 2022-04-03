package dev.rodrigoazlima.troublesomenetworkcard.executor.runtime;

import dev.rodrigoazlima.troublesomenetworkcard.executor.Executor;
import dev.rodrigoazlima.troublesomenetworkcard.executor.ExecutorResultChewer;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

public class RuntimeExecutor implements Executor {

    @Override
    public void execute(final String[][] commands, final ExecutorResultChewer chewer) {
        Arrays.stream(commands).forEach(command -> execute(command, chewer));
    }

    public void execute(final String[] command, final ExecutorResultChewer chewer) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // Read the output from the command
            System.out.println(new Date() + " " + "Standard output of the command:\n");
            StringBuilder outBuilder = new StringBuilder();
            StringBuilder errBuilder = new StringBuilder();
            String out = null;
            while ((out = stdInput.readLine()) != null) {
                System.out.println(new Date() + " " + out);
                outBuilder.append(out);
            }

            // Read any errors from the attempted command
            String err = null;
            System.err.println(new Date() + " Standard error of the command (if any):\n");
            while ((err = stdError.readLine()) != null) {
                System.err.println(new Date() + " " + err);
                errBuilder.append(err);
            }
            if (chewer != null) {
                chewer.chew(outBuilder.toString(), errBuilder.toString());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
