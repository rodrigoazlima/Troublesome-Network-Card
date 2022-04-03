package dev.rodrigoazlima.troublesomenetworkcard.runnable;

import dev.rodrigoazlima.troublesomenetworkcard.executor.runtime.RuntimeExecutor;

public class PingRunnable implements Runnable {
    public static final int DELAY_MILLIS = 1;
    public static final String[][] pingCommand = {{"ping", "-w", "5", "8.8.8.8"}};
    /**
     * Commands that fixes my network card problems.
     *
     * @see {https://www.intel.com/content/www/us/en/support/articles/000058982/wireless/intel-killer-wireless-products.html}
     */
    public static final String[][] resetCommand = {{"ipconfig", "/release"},//
            {"ipconfig", "/flushdns"},//
            {"ipconfig", "/renew"},//
            {"netsh", "int", "ip", "reset"},//
            {"netsh", "winsock", "reset"}};
    public static final int TIMED_OUT_MAX_BEFORE_RESET = 3;

    @Override
    public void run() {
        while (true) {
            new RuntimeExecutor().execute(pingCommand, (out, err) -> {
                String[] timedOutSplit = out.split("timed out");
                if (timedOutSplit.length > TIMED_OUT_MAX_BEFORE_RESET) {
                    new RuntimeExecutor().execute(resetCommand, null);
                }
            });
            try {
                Thread.sleep(DELAY_MILLIS);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
