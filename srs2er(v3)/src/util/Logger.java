package util;

public class Logger {
	private static boolean Log = true;
	
	/*New Instance cannot be created*/
	private Logger() {
		
	}
	
	public static void Log(String log) {
		if (Log == true) {
			System.out.println(log);
		}
	}
}
