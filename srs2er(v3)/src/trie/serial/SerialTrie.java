package trie.serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.RelationEntity;
import nlp.objects.Relationship;
import nlp.objects.Sentence;
import nlp.objects.Word;
import nlp.processing.EditDistance;
import nlp.processing.EditDistance.Operation;
import nlp.test.LookupResultObject;
import nlp.test.TestSentence;
import trie.Node;
import trie.Trie;
import erTagger.ERTagger;

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

	public void assignLookupResults(TestSentence sentence)
	{
		for (Branch branch : branches)
		{
			int cost = EditDistance.editDistance(sentence, branch);
			if (cost < 10)
			{
				List<Operation> ops = EditDistance.editDistanceExtended(sentence, branch).second();
				Sentence copy = EditDistance.updateWordIndexes(sentence, ops);

				updateDataModel(copy, branch.leafInformation.getDataModel());

				LookupResultObject obj = new LookupResultObject(cost,
						branch.leafInformation.getDataModel(), branch);
				sentence.addLookupResult(obj);

				// System.out.println(branch.leafInformation.getDataModel());
				// System.out.println(branch.sentences.get(0));
				// System.out.println(branch);
				// System.out.println(sentence);
				// System.out.println(ops);
				// System.out.println("---------------");
			}
		}

	}

	public static void updateDataModel(Sentence sent, Model model)
	{
		// Model modelToUpdate = sent.getDataModel();
		for (Entity entity : model.getEntities())
		{
			int wordId = entity.getWordIndex();
			int length = entity.getLength();
			entity.setName(getLabelWith(sent, wordId, length));
			for (Attribute attribute : entity.getAttributes())
			{
				wordId = attribute.getWordIndex();
				length = attribute.getLength();
				attribute.setName(getLabelWith(sent, wordId, length));
			}
		}

		for (Relationship relationship : model.getRelationships())
		{
			int wordId = relationship.getWordIndex();
			int length = relationship.getLength();
			relationship.setName(getLabelWith(sent, wordId, length));
			for (RelationEntity relation : relationship.getConnects())
			{
				// wordId = relation.getWordIndex();
				// length = relation.getLength();
				// relation.setName(getLabelWith(sent, wordId, length));
			}
		}

	}
	private static String getLabelWith(Sentence sent, int wordIndex, int length)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<Word> itr = sent.getTokens().iterator();
		while (itr.hasNext())
		{
			Word curr = itr.next();
			if (curr.getId() == wordIndex)
			{
				sb.append(curr.getLemmatizedName() + " ");
				length--;
				break;
			}
			else if (wordIndex < curr.getId())
			{
				return "!Failed";
			}
			else
				;
		}
		while (length > 0)
		{
			Word curr = itr.next();
			if (curr.getId() != -1)
			{
				sb.append(curr.getLemmatizedName() + " ");
				length--;
			}
		}

		return sb.toString();
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

		int numberOfResultsAsOutput = 4;
		for (Integer cost : costs.keySet())
		{
			if (numberOfResultsAsOutput > 0)
			{
				System.out.println("Matches with cost: " + cost);
				for (Branch branch : costs.get(cost))
				{
					numberOfResultsAsOutput--;
					System.out.println("POST of the branch:");
					System.out.println(branch.toString("[%-4s] "));

					List<Operation> ops = EditDistance.editDistanceExtended(sentence, branch)
							.second();

					Sentence sent_copy = EditDistance.updateWordIndexes(sentence, ops);

					System.out.println(sent_copy.toWordIndexString());
					// System.out.println(ops);
					System.out.println(branch.sentences);
					System.out.println(branch.leafInformation.getDataModel().toString());

					/*
					 * List<Word> updatedWords = new LinkedList<Word>(); for
					 * (Word token : sent_copy.getTokens()) { if (token.getId()
					 * != -1) { updatedWords.add(token); } }
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
