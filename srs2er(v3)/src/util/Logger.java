package util;

public class Logger {
	/*public enum Priority {
		LOG(10),
		WARNING(5),
		ERROR(0) 
	}*/
	private static boolean Log = true;
	
	/*New Instance cannot be created*/
	private Logger() {
		
	}
	
	public static void Log(String log) {
		if (Log == true) {
			System.out.println(log);
		}
	}
	
	public static void DontLog() {
		Log = false;
	}
}
