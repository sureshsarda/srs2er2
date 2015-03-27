package trie.serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ERTagger.ERTagger;
import nlp.objects.Sentence;
import nlp.processing.EditDistance;
import nlp.processing.EditDistance.Operation;
import trie.Node;
import trie.Trie;

/*
 * 
 * FIXME This code was written to test Edit Distance. It needs to be updated.
 */
public class SerialTrie
{
	List<Branch> branches;

	public SerialTrie(Trie trie)
	{
		List<Node> roots = trie.getRoot();
		branches = new ArrayList<Branch>();
		for (Node node : roots)
		{
			List<Branch> serialized = exploreNode(node);

			branches.addAll(serialized);
		}
	}

	private List<Branch> exploreNode(Node node)
	{
		List<Branch> childBranches = new ArrayList<Branch>();

		if (node.getChildren().size() == 0)
		{
			Branch branch = new Branch();
			branch.add(new SerialNode(node.getTag()));
			branch.leafInformation = node.getLeafInformation();
			branch.sentences = node.getLeafInformation().sentences;
			childBranches.add(branch);

		}
		else
		{
			for (Node child : node.getChildren())
			{
				for (Branch childBranch : exploreNode(child))
				{
					childBranch.add(0, new SerialNode(node.getTag()));
					childBranches.add(childBranch);
				}
			}
		}
		return childBranches;
	}

	public void Lookup(Sentence sentence)
	{
		/* Hash map with chaining */
		Map<Integer, List<Branch>> costs = new HashMap<Integer, List<Branch>>();

		ERTagger.LOGGER.info("Looking up for: " + sentence);

		for (Branch branch : branches)
		{
			ERTagger.LOGGER.config("Looking up against: " + branch.toString("[%-4s] "));
			int cost = EditDistance.editDistance(sentence, branch);
			ERTagger.LOGGER.config("Cost = " + cost);

			/* Add the branch and its cost to Map */
			if (costs.containsKey(cost) == false)
			{
				costs.put(cost, new LinkedList<Branch>());
			}

			List<Branch> br = costs.get(cost);
			br.add(branch);

			costs.put(cost, br);
		}

		for (Integer cost : costs.keySet())
		{
			/*
			 * Change cost to include more results. All the branches with cost
			 * less than this threshold will be displayed.
			 */
			if (cost < 5) {
			System.out.println("Matches with cost: " + cost);
			for (Branch branch : costs.get(cost))
			{
				System.out.println("POST of the branch:");
				System.out.println(branch.toString("[%-4s] "));

				List<Operation> ops = EditDistance.editDistanceExtended(sentence, branch).second();

				Sentence sent_copy = EditDistance.updateWordIndexes(sentence, ops);

				System.out.println(sent_copy.toWordIndexString());
				// System.out.println(ops);
				System.out.println(branch.sentences);
				System.out.println(branch.leafInformation.getDataModel().toString());

				/*
				 * List<Word> updatedWords = new LinkedList<Word>(); for (Word
				 * token : sent_copy.getTokens()) { if (token.getId() != -1) {
				 * updatedWords.add(token); } }
				 * sent_copy.setTokens(updatedWords);
				 * 
				 * Lookup.processDataModel(sent_copy,
				 * branch.leafInformation.getDataModel());
				 */
				// System.out.println(branch.leafInformation.getDataModel());
				System.out
						.println("-------------------------------------------------------------------------------------");
				// /System.out.printf("\n%80s\n", "");
			}
			}
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (Branch branch : branches)
		{
			sb.append(branch.toString("[%-4s] "));
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}
}
