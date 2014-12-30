package util;

public class Logger {
	/*public static enum Priority {
		LOG(10),
		WARNING(5),
		ERROR(0);
		
		private static Priority value = LOG;
		
		 Priority(Priority value) {
			this.value = value;
		}
	}*/
	
	/*private static Integer PRIORITY_LOG = 10;
	private static Integer PRIORITY_WARNING = 5;
	private static Integer PRIORITY_ERROR = 0;*/
	
	private static Integer PRIORITY = 0;//PRIORITY_LOG;
	
	/*New Instance cannot be created*/
	private Logger() {
		
	}
	public static void Log(String logString, Integer priority) {
		if (PRIORITY >= priority) {
			System.out.println(logString);
		}
	}
	
	public static void Log(String logString) {
		//System.out.println(logString);
	}
	
	
}
