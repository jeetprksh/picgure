package com.picgure.logging;

import java.util.logging.*;

/*
 * @author Jeet Prakash
 * */
public class PicgureLogger {

    private static Logger logger = Logger.getLogger(PicgureLogger.class.getName());

    private static Handler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("%h/.picgure/log/picgure-%u-%g.log", 1_048_576, 10, true);
            fileHandler.setFormatter(new LogFormatter());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.severe("Error creating file handler for logger");
        }
    }

    public static Logger getLogger(Class klass) {
        Logger appLogger = Logger.getLogger(klass.getName());
        appLogger.setLevel(Level.FINE);

        appLogger.addHandler(new ConsoleHandler());
        appLogger.addHandler(fileHandler);
        return appLogger;
    }
}
