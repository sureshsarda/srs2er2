package trie.serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nlp.objects.Sentence;
import nlp.processing.EditDistance;
import trie.LeafNode;
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
			branch.leafInformation = node.getLeafInformation();
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

	public void Lookup(Sentence sentence) {
		/* Hash map with chaining */
		Map<Integer, List<Branch>> costs = new HashMap<Integer, List<Branch>>();
		for (Branch branch : branches) {
			int cost = EditDistance.editDistance(sentence, branch);

			if (costs.containsKey(cost) == false) {
				costs.put(cost, new LinkedList<Branch>());
			}

			List<Branch> br = costs.get(cost);
			br.add(branch);

			costs.put(cost, br);
		}

		for (Integer cost : costs.keySet()) {
			System.out.println("Matches with cost: " + cost);
			if (cost < 4) {
				for (Branch branch : costs.get(cost)) {
					System.out.println(branch);
					System.out.println(branch.leafInformation.getDataModel().toString());
				}
			}
			else {
				System.out.println("There are results but the cost exceeds the threshold. Hence skipped.");
			}
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Branch branch : branches) {
			sb.append(branch.toString("[%-4s] "));
			sb.append(System.lineSeparator());
		}

		
		return sb.toString();
	}
}
