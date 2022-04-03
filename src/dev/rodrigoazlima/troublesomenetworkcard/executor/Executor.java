package dev.rodrigoazlima.troublesomenetworkcard.executor;

public interface Executor {

    void execute(final String[][] commands, final ExecutorResultChewer chewer);
}
