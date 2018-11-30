package com.picgure.logging;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/*
 * @author Jeet Prakash
 * */
public class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return record.getSourceClassName() + " "
                + record.getSourceMethodName() + " "
                + new Date(record.getMillis()) + " "
                + record.getMessage() + "\n";
    }

}
