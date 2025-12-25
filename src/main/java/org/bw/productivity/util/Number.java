package org.bw.productivity.util;

import java.util.Calendar;

public class Number {

    public static String formatNumber(int number) {
        String numberAsString = String.valueOf(number);
        if (number < 10 && number >= 0) {
            numberAsString = "0" + numberAsString;
        }
        return numberAsString;
    }
}
