package trie.serial;

public class SerialNode {
	String tag;
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public SerialNode(String tag) {
		setTag(tag);
	}
	
	public String toString() {
		return tag;
	}
	
}
