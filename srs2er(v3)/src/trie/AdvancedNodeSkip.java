package trie;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nlp.objects.Sentence;

public class AdvancedNodeSkip {
	public static double THRESHOLD = 0.6;

	public static void apply(Sentence sentence, Trie trie) {
		Queue<Node> untraversedNodes = new LinkedList<Node>();

		/* Initial Seed*/
		for (Node node : trie.Root) {
			untraversedNodes.add(node);
		}
		
		while (untraversedNodes.isEmpty() == false) {
			
		}
	}

	private static void probablePaths(String tag) {

	}
	
	private static List<Node> exploreNode(Node node) {
		List<Node> children = node.getChildren();
		List<Node> probablePaths = new LinkedList<Node>();
		
		for (Node child : children) {
			if (child.getIsStopWordProbability() > 0.5) {
				/*If it is a stop word node, then explore more*/
				probablePaths.addAll(exploreNode(child));
			}
			else {
				/*Concrete node, directly add*/
				probablePaths.add(child);
			}
		}
		return probablePaths;
	}

}
