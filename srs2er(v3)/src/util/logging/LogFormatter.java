package util.logging;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {
	
	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);
		
		buf.append(formatMessage(record));
		buf.append(System.lineSeparator());
		return buf.toString();
	}
}
