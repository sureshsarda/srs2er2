package trie;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import nlp.objects.Sentence;
import util.Tuple;

/**
 * All the lookup algorithms and functions
 * 
 * @author Suresh Sarda
 *
 */
public class Lookup {

	private static final Integer EXACTLY_SAME = 0;
	private static final Integer FAMILY_SAME = 25;
	private static final Integer DIFFERENT = 100;

	public static LeafNode lookup(Trie trie, Sentence sentence,
			Integer tolerance) {
		Stack<Tuple<Node, Integer>> untraversedPivots = new Stack<Tuple<Node, Integer>>();
		List<Tuple<Node, Integer>> probableLeaves = new LinkedList<Tuple<Node, Integer>>();
		int level = 0; /* Token index */

		return null;
	}

	private static List<Tuple<Node, Integer>> probablePaths(Node pivot,
			String post) {
		List<Node> allPaths = pivot.getChildren();
		List<Tuple<Node, Integer>> probablePaths = new LinkedList<Tuple<Node, Integer>>();
		Iterator<Node> path = allPaths.iterator();

		while (path.hasNext()) {
			Node current = path.next();
			

		}
		return null;
	}

	public static Tuple<Node, Integer> searchList(List<Node> list, String post) {

		return null;
	}

	public static LeafNode strictMatch(Trie trie, Sentence sentence) {
		Node firstParent = searchListStrict(trie.Root, sentence.getTokens()
				.get(0).getPost());

		if (firstParent != null) {
			/* First parent found */
		} else {
			/* No first parent found. Use approximations */
		}
		return null;
	}

	private static Node searchListStrict(List<Node> list, String post) {
		Iterator<Node> itr = list.iterator();
		while (itr.hasNext()) {
			Node current = itr.next();
			if (current.getTag().compareTo(post) == 0) {
				return current;
			}
		}
		return null;
	}

	private Integer CalculateTagDifferenceCost(String tag1, String tag2) {
		if (tag1.compareTo(tag2) == 0) {
			return EXACTLY_SAME;
		} else if (tag1.charAt(0) == tag2.charAt(0)
				&& tag1.charAt(1) == tag2.charAt(1)) {
			return FAMILY_SAME;
		} else {
			return DIFFERENT;
		}
	}
}
