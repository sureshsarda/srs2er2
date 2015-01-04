package util.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class LoggerSetup {

	static public void setup(java.util.logging.Logger logger) throws IOException {
		
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.INFO);

		LogFormatter formatter = new LogFormatter();
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		handler.setFormatter(formatter);

		logger.addHandler(handler);
	}

	public static void Log(String message) {

	}

}
