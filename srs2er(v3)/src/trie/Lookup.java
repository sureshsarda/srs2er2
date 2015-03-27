package trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.RelationEntity;
import nlp.objects.Relationship;
import nlp.objects.Sentence;
import nlp.objects.Word;
import srs2er.ERTagger;
import util.Name;
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
	private static final Integer DIFFERENT = 50;

	public static LeafNode lookup(Trie trie, Sentence sentence,
			Tuple<Integer, Integer> cost) {

		LeafNode leaf = strictMatch(trie, sentence);
		if (leaf == null) {
			ERTagger.LOGGER.warning("Exact not match found.");
		} else {
			/*Leaf is not null*/
			if (leaf.getDataModel() == null) {
				ERTagger.LOGGER
						.warning("Exact Match Found but Data Model is not present at this level.");
			}
			else {
				return leaf;
			}

		}
		/*Exact match not found*/
		ERTagger.LOGGER.info("Applying AdvancedLookup Algorithm");
		advancedLookupPrettyPrint(sentence, advancedLookup(trie, sentence, cost));
		
		return null;
	}

	private static LeafNode strictMatch(Trie trie, Sentence sentence) {

		Tuple<Node, Integer> base = searchList(trie.Root, sentence.getTokens()
				.get(0).getPost());

		if (base.second == EXACTLY_SAME) {
			return searchRemaining(base.first(), sentence);
		} else if (base.second == FAMILY_SAME) {
			ERTagger.LOGGER.config(String.format(
					"Lookup: [%s] matched in family. WordIndex = 0", sentence
							.getTokens().get(0).getPost()));
			return searchRemaining(base.first(), sentence);
		} else {
			return null;
		}
	}

	private static LeafNode searchRemaining(Node parent, Sentence sentence) {
		Iterator<Word> tokenIterator = sentence.getTokens().iterator();
		tokenIterator.next(); // skip the parent node as it is already checked

		while (tokenIterator.hasNext()) {
			Word currentWord = tokenIterator.next();
			Tuple<Node, Integer> searchResult = searchList(
					parent.getChildren(), currentWord.getPost());

			if (searchResult.second() == EXACTLY_SAME) {
				parent = searchResult.first();
			} else if (searchResult.second == FAMILY_SAME) {
				ERTagger.LOGGER.config(String.format(
						"Lookup: %s matched in family. WordIndex = %d",
						currentWord.getPost(), currentWord.getId()));
				parent = searchResult.first();
			} else {
				ERTagger.LOGGER.config(String.format("Lookup Failed.",
						sentence.getValue()));
				ERTagger.LOGGER
						.info(String
								.format("Trying to match: [%s]. But parent has children: [%s]",
										currentWord.toString(), parent
												.getChildren().toString()));
				return null;
			}

		}

		if (parent.getLeafInformation() != null) {
			processDataModel(sentence, parent.getLeafInformation()
					.getDataModel());
		}

		return parent.getLeafInformation();
	}

	public static void processDataModel(Sentence sentence, Model model) {

		for (Entity entity : model.getEntities()) {
			entity.setName(Name.buildName(sentence.getTokens(),
					entity.getWordIndex(), entity.getLength()));
			for (Attribute attribute : entity.getAttributes()) {
				attribute.setName(Name.buildName(sentence.getTokens(),
						attribute.getWordIndex(), attribute.getLength()));
			}
		}

		for (Relationship relationship : model.getRelationships()) {
			relationship.setName(Name.buildName(sentence.getTokens(),
					relationship.getWordIndex(), relationship.getLength()));
			for (RelationEntity re : relationship.getConnects()) {
				re.setName(model.getEntities().get(re.getEntityId())
						.getLemmName());
			}
		}
	}

	private static Tuple<Node, Integer> searchList(List<Node> list, String post) {

		/* Search for exact match */
		Iterator<Node> itr = list.iterator();
		while (itr.hasNext()) {
			Node current = itr.next();
			Integer differenceCost = CalculateTagDifferenceCost(
					current.getTag(), post);
			if (differenceCost <= EXACTLY_SAME) {
				return new Tuple<Node, Integer>(current, EXACTLY_SAME);
			}
		}

		/* Search for same family */
		itr = list.iterator();
		while (itr.hasNext()) {
			Node current = itr.next();
			Integer differenceCost = CalculateTagDifferenceCost(
					current.getTag(), post);
			if (differenceCost <= FAMILY_SAME) {
				return new Tuple<Node, Integer>(current, FAMILY_SAME);
			}
		}

		/* Tags are different */
		return new Tuple<Node, Integer>(null, DIFFERENT);

	}

	private static Integer CalculateTagDifferenceCost(String tag1, String tag2) {
		if (tag1.compareTo(tag2) == 0) {
			return EXACTLY_SAME;
		} else if (tag1.charAt(0) == tag2.charAt(0)
				&& tag1.charAt(1) == tag2.charAt(1)) {
			return FAMILY_SAME;
		} else {
			return DIFFERENT;
		}
	}

	/*
	 * ------------------------------------------------------------------------
	 * ADVANCED LOOK UP FUNCTIONS
	 * ------------------------------------------------------------------------
	 */

	private static List<LeafNode> advancedLookupPrettyPrint(Sentence sentence, Map<Node, Tuple<Integer, Integer>> possibleNodes) {
		for (Node node : possibleNodes.keySet()) {
			try {
				Tuple<Integer, Integer> cst = possibleNodes.get(node);
				processDataModel(sentence, node.getLeafInformation()
						.getDataModel());
				
				ERTagger.LOGGER.info(String.format("Cost: %d Unmatched: %d",
						cst.first(), cst.second()));
				ERTagger.LOGGER.info(node.getLeafInformation().getDataModel()
						.toString());
			} catch (NullPointerException npe) {
				ERTagger.LOGGER.fine("No leaf node found at this level.");
			}
		}

		return null;
	}

	private static Map<Node, Tuple<Integer, Integer>> advancedLookup(Trie trie,
			Sentence sentence, Tuple<Integer, Integer> cost) {

		int level = 0; /* Token index */
		Iterator<Word> tokenItr = sentence.getTokens().iterator();

		/* Node, CostTillNow, Not Matched Node Count */
		Map<Node, Tuple<Integer, Integer>> probablePaths = new HashMap<Node, Tuple<Integer, Integer>>();
		Word rootWord = tokenItr.next();
		for (Node node : trie.Root) {
			Integer thisCost = CalculateTagDifferenceCost(node.getTag(),
					rootWord.getPost());
			if (thisCost == DIFFERENT) {
				probablePaths.put(node,
						new Tuple<Integer, Integer>(thisCost, 1));
			} else {
				probablePaths.put(node,
						new Tuple<Integer, Integer>(thisCost, 0));
			}
		}

		while (tokenItr.hasNext()) {
			Word currentWord = tokenItr.next();
			Set<Node> keySet = new HashSet<Node>();
			keySet.addAll(probablePaths.keySet());

			for (Node node : keySet) {
				Tuple<Integer, Integer> costTillNow = probablePaths.get(node);
				if (node.getChildren().isEmpty() == true) {
					continue;
				}
				for (Node child : node.getChildren()) {
					Integer childCost = CalculateTagDifferenceCost(
							child.getTag(), currentWord.getPost());
					if (childCost + costTillNow.first() <= cost.first()) {
						if (childCost == DIFFERENT) {
							probablePaths.put(
									child,
									new Tuple<Integer, Integer>(childCost
											+ costTillNow.first(), costTillNow
											.second() + 1));
						} else {
							probablePaths.put(
									child,
									new Tuple<Integer, Integer>(childCost
											+ costTillNow.first(), costTillNow
											.second()));
						}
					}
				}
				probablePaths.remove(node);
			}
		}

		return probablePaths;
	}

	private static List<Node> findFamilyMatch(List<Node> list, String post) {
		Iterator<Node> itr = list.iterator();
		List<Node> match = new LinkedList<Node>();

		while (itr.hasNext()) {
			Node current = itr.next();
			if (CalculateTagDifferenceCost(post, current.getTag()) == FAMILY_SAME) {
				match.add(current);
			}
		}
		return match;
	}

	private static Node findExactMatch(List<Node> list, String post) {
		Iterator<Node> itr = list.iterator();
		while (itr.hasNext()) {
			Node current = itr.next();
			if (CalculateTagDifferenceCost(post, current.getTag()) == EXACTLY_SAME) {
				return current;
			}
		}
		return null;
	}

}
