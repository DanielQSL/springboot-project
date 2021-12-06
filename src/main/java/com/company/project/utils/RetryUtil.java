package com.company.project.utils;

/**
 * 重试工具类
 *
 * @author DanielQSL
 */
public class RetryUtil {

    private RetryUtil() {

    }

    public static Object executeIfNotError(Runnable runnable, int retryTime, int sleepTime) {
        for (int i = 0; i < retryTime; i++) {
            try {
                runnable.run();
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Object executeIfNotTrue(TrueRunnable runnable, int retryTime, int sleepTime) {
        Boolean result = false;
        for (int i = 0; i < retryTime; i++) {
            result = runnable.run();
            if (result) {
                break;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @FunctionalInterface
    public interface TrueRunnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately execu
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see java.lang.Thread#run()
         */
        Boolean run();
    }

}
