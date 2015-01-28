package trie.serial;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import trie.Node;
import trie.Trie;

public class SerialTrie {
	List<Branch> branches;

	public SerialTrie(Trie trie) {
		List<Node> roots = trie.getRoot();
		branches = new ArrayList<Branch>();
		for (Node node : roots) {
			List<Branch> serialized = exploreNode(node);
			
			branches.addAll(serialized);
		}
	}

	private List<Branch> exploreNode(Node node) {
		List<Branch> childBranches = new ArrayList<Branch>();

		if (node.getChildren().size() == 0) {
			Branch branch = new Branch();
			branch.add(new SerialNode(node.getTag()));
			childBranches.add(branch);
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
			sb.append(branch.toString("[%-4s] "));
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
}
