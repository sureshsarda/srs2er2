package nlp.objects;

import nlp.objects.TagDataLoader.TagType;

public class Tag implements Comparable<Tag> {

    public enum TAG_DIFFERENCE {
	SAME, FAMILY, DIFFERENT
    }

    TagType tagType;
    
    public Tag(String name) {
	try {
	    tagType = TagType.valueOf(name);    
	}
	catch (IllegalArgumentException ex) {
	    tagType = TagType.UNKNOWN;
	}
	
    }
    
    public TagType getTagType() {
	return tagType;
    }

    public String toString() {
	return tagType.toString();
    }
    
    public String toLongString() {
	return tagType.toLongString();
    }
    public static TAG_DIFFERENCE costDifference(String tag1, String tag2) {
	if (tag1.charAt(0) == tag2.charAt(0)) {
	    if (tag1.charAt(1) == tag2.charAt(1)) {
		return TAG_DIFFERENCE.SAME;
	    } else {
		return TAG_DIFFERENCE.FAMILY;
	    }
	} else {
	    return TAG_DIFFERENCE.DIFFERENT;
	}
    }

    @Override
    public int compareTo(Tag tag) {
	if (this.tagType == tag.tagType)
	    return 0;
	else 
	    return -1;
    }

}
