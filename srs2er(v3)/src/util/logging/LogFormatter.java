package util.logging;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {
	
	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);
		
		/*Prints the name of the Logger*/
//		buf.append(record.getLoggerName());
//		buf.append(System.lineSeparator());
		
		buf.append(record.getLevel() + ": ");
		buf.append(formatMessage(record));
		buf.append(System.lineSeparator());
		return buf.toString();
	}
}
