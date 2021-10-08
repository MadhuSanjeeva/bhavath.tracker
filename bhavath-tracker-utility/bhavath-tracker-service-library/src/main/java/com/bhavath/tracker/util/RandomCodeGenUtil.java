package com.bhavath.tracker.util;

import me.xdrop.jrand.JRand;

public class RandomCodeGenUtil {
    public static synchronized Integer createRandomInteger() {
        return JRand.natural().range(0000001,999999).gen();
    }
}
