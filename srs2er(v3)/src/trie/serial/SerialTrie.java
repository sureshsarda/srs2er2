package trie.serial;

import java.util.ArrayList;
import java.util.List;

import trie.Node;
import trie.Trie;

public class SerialTrie {
	List<Branch> branches;

	public SerialTrie(Trie trie) {
		List<Node> roots = trie.getRoot();

		for (Node node : roots) {
			branches.addAll(exploreNode(node));
		}
	}

	private List<Branch> exploreNode(Node node) {
		List<Branch> childBranches = new ArrayList<Branch>();

		if (node.getChildren().size() == 0) {
			Branch branch = new Branch();
			branch.add(new SerialNode(node.getTag()));
		} else {
			for (Node child : node.getChildren()) {
				for (Branch childBranch : exploreNode(child)) {
					childBranch.add(0, new SerialNode(node.getTag()));
					childBranches.add(childBranch);
				}
			}
		}
		return childBranches;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Branch branch : branches) {
			sb.append(branch.toString());
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
}
