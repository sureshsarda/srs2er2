package trie;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nlp.objects.Sentence;
import nlp.objects.Tag;
import nlp.objects.Tag.TAG_DIFFERENCE;
import util.Tuple;

public class AdvancedNodeSkip {
	public static double THRESHOLD = 0.6;

	public static void apply(Sentence sentence, Trie trie) {
		Queue<Tuple<Node, Integer>> untraversedNodes = new LinkedList<Tuple<Node, Integer>>();

		/* Initial Seed */
		for (Node node : trie.Root) {
			untraversedNodes.add(new Tuple<Node, Integer>(node, 0));
		}

		while (untraversedNodes.isEmpty() == false) {

		}
	}

	private static List<Node> probablePaths(List<Node> untraversedNodes,
			String tag) {
		List<Node> probablePath = new LinkedList<Node>();

		for (Node node : untraversedNodes) {
			List<Node> allPossible = exploreNode(node);
			for (Node possible : allPossible) {
				if (possible.getTag().compareTo(tag) == 0) {
					probablePath.add(possible);
				}
			}
		}
		return probablePath;
	}

	private static List<Node> exploreNode(Node node) {
		List<Node> children = node.getChildren();
		List<Node> probablePaths = new LinkedList<Node>();

		for (Node child : children) {
			if (child.getIsStopWordProbability() > 0.5) {
				/* If it is a stop word node, then explore more */
				probablePaths.addAll(exploreNode(child));
			} else {
				/* Concrete node, directly add */
				probablePaths.add(child);
			}
		}
		return probablePaths;
	}

}
