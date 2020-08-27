package org.nazriaz.instagrammer.Util;

public class RandomDelayUtill {
    public static void delay(long ms) {
        long part1 = ms / 3;
        long part2 = ms - part1;
        long result = part1 + Math.round(Math.random() * part2 * 2);
        try {
            Thread.sleep(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
