package com.hiccup.misc;

import org.apache.log4j.Logger;

/**
 * Created by wenhy on 2018/1/16.
 */
public class Log4JTest {

    /**
     * Apache log4j 日志框架测试
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Log4JTest.class);
        logger.fatal("fatal msg");
        logger.error("error msg");
        logger.warn("warn msg");
        logger.info("info msg");
        logger.debug("debug msg");
        logger.trace("trace msg");

    }

}
