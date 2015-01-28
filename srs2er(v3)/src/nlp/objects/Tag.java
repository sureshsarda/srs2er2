package nlp.objects;

public class Tag implements Comparable<Tag>{

	protected String tag;
	
	public enum TAG_DIFFERENCE {
		SAME, FAMILY, DIFFERENT
	}
	
	public static Integer costDifference(Tag tag1, Tag tag2) {
		return 0;
	}
	
	public static TAG_DIFFERENCE costDifference(String tag1, String tag2) {
		if (tag1.charAt(0) == tag2.charAt(0)) {
			if (tag1.charAt(1) == tag2.charAt(1)) {
				return TAG_DIFFERENCE.SAME;
			}
			else {
				return TAG_DIFFERENCE.FAMILY;
			}
		}
		else {
			return TAG_DIFFERENCE.DIFFERENT;
		}
	}
	
	
	public boolean equals(Tag tag) {
		return false;
	}
	
	@Override
	public String toString() {
		return null;
	}

	@Override
	public int compareTo(Tag tag) {

		return 0;
	}

	
}
