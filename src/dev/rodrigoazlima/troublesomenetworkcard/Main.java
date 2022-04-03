package dev.rodrigoazlima.troublesomenetworkcard;

import dev.rodrigoazlima.troublesomenetworkcard.runnable.PingRunnable;

public class Main {
    private final static Thread threadPing = new Thread(new PingRunnable());

    public static void main(String[] args) {
        System.out.println("Starting Troublesome Network Card program.");
        threadPing.run();
    }
}
