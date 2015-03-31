package util.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerSetup {

	static public void setup(Logger logger) {
		
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);

		LogFormatter formatter = new LogFormatter();
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		handler.setFormatter(formatter);

		logger.addHandler(handler);
	}


}
