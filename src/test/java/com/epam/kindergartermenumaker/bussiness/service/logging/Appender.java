package com.epam.kindergartermenumaker.bussiness.service.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
class Appender {
    static ListAppender<ILoggingEvent> getAppenders(Class clazz) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);

        ListAppender<ILoggingEvent> loggingEventListAppender = new ListAppender<>();
        loggingEventListAppender.start();

        logger.addAppender(loggingEventListAppender);

        return loggingEventListAppender;
    }
}