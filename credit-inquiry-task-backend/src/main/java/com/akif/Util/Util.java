package com.akif.Util;

import java.math.BigDecimal;

public class Util {
    public static int getDecimal(long number) {
        BigDecimal bd = new BigDecimal(Long.toString(number));
        return bd.precision();
    }
}
