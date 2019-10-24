package top.hiccup.jdk.lang.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池执行任务时可以指定异常处理器
 *
 * 用法：Thread.setDefaultUncaughtExceptionHandler(new CaughtExceptionHandler());
 *
 * @author wenhy
 * @date 2019/10/22
 */
public class CaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error(t.getName(), e);
    }
}

