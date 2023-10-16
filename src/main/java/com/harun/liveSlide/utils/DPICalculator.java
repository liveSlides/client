package com.harun.liveSlide.utils;

public class DPICalculator {
    public static int calculateDPI(int numberOfPage) {
        if (numberOfPage < 100) {
            return 200;
        }
        else if (numberOfPage < 200) {
            return 100;
        }
        else if (numberOfPage < 400) {
            return 75;
        }
        else {
            return 40;
        }
    }
}
