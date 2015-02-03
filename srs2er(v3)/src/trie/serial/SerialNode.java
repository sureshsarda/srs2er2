package trie.serial;

import nlp.objects.Tag;

public class SerialNode implements Comparable<SerialNode>{
	Tag tag;
	
	public Tag getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = new Tag(tag);
	}
	
	public SerialNode(String tag) {
		setTag(tag);
	}
	
	public String toString() {
		return tag.toString();
	}

	@Override
	public int compareTo(SerialNode node) {
		return tag.compareTo(node.tag);
	}
	
}
