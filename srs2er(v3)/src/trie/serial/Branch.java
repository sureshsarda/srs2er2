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
		StringBuilder sb = new StringBuilder();
		for (SerialNode serialNode : nodes) {
			sb.append("[");
			sb.append(serialNode.toString());
			sb.append("] ");
		}
		return sb.toString();
	}
}
