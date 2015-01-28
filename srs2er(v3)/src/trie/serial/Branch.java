package trie.serial;

import java.util.ArrayList;
import java.util.List;

public class Branch {
	List<SerialNode> nodes = new ArrayList<SerialNode>();
	
	public Branch() {
		
	}
	
	public void add(SerialNode node) {
		nodes.add(node);
	}
	public void addAll(List<SerialNode> nodes) {
		this.nodes.addAll(nodes);
	}
	
	public void add(int index, SerialNode node) {
		nodes.add(index, node);
	}
	
	public String toString() {
		return toString("[%s] ");
	}
	
	public String toString(String format) {
		StringBuilder sb = new StringBuilder();
		for (SerialNode serialNode : nodes) {
			sb.append(String.format(format, serialNode.toString()));
		}
		return sb.toString();
	}
}
