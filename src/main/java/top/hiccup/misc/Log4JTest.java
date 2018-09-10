package top.hiccup.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenhy on 2018/1/16.
 */
public class Log4JTest {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Log4JTest.class);
//        logger.fatal("fatal msg");
        logger.error("error msg");
        logger.warn("warn msg");
        logger.info("info msg");
        logger.debug("debug msg");
        logger.trace("trace msg");

    }

}
