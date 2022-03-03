package top.hiccup.misc.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * log4j
 *
 * @author wenhy
 * @date 2019/9/16
 */
public class Log4j2Test {

    private static final Logger LOGGER = LogManager.getLogger(Log4j2Test.class);

    public static void main(String[] args) {
        LOGGER.info("测试测试测试");
    }
}
