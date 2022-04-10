package com.akif.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Lggr {
    private static Lggr instance;

    private final Logger logger = LoggerFactory.getLogger(Lggr.class);

    private Lggr() {
    }

    public static Lggr getInstance() {
        if (instance == null) {
            instance = new Lggr();
        }
        return instance;
    }

    public void info(String log) {
        logger.info(log);
    }

    public void debug(String log) {
        logger.debug(log);
    }

    public void error(String log) {
        logger.error(log);
    }
}
