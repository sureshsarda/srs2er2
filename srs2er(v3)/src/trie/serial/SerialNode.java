package trie.serial;

public class SerialNode implements Comparable<SerialNode>{
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

	@Override
	public int compareTo(SerialNode node) {
		return tag.compareTo(node.tag);
	}
	
}
