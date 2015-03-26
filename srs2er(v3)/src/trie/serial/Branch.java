package trie.serial;

import java.util.ArrayList;
import java.util.List;

import trie.LeafNode;

public class Branch implements Comparable<Branch> {
	List<SerialNode> nodes = new ArrayList<SerialNode>();
	LeafNode leafInformation;
	List<String> sentences;
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

	@Override
	public String toString() {
		return toString("[%s] ");
	}

	public int size() {
		return nodes.size();
	}

	public SerialNode get(int i) {
		return nodes.get(i);
	}

	public String toString(String format) {
		StringBuilder sb = new StringBuilder();
		for (SerialNode serialNode : nodes) {
			sb.append(String.format(format, serialNode.toString()));
		}
		
		//sb.append(System.lineSeparator());
		//sb.append(leafInformation.toString());
		
		return sb.toString();
	}

	@Override
	public int compareTo(Branch branch) {
		if (size() > branch.size()) {
			return 1;
		} else if (size() < branch.size()) {
			return -1;
		} else {
			/* Size is not everything */
			for (int i = 0; i < size(); i++) {
				int compare = nodes.get(i).getTag()
						.compareTo(branch.nodes.get(i).getTag());
				if (compare == -1) {
					return -1;
				} else if (compare == 1) {
					return 1;
				} else {
					continue;
				}
			}
		}
		return 0;
	}
}
