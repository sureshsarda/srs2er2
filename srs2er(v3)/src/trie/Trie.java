package trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.Sentences;
import nlp.objects.Word;
/**
 * Trie:
 * 
 * @author Suresh Sarda
 *
 */
public class Trie {
	private List<Node> Root;
	
	/**
	 * Default constructor. Intilizes all the elements.
	 */
	public Trie() {
		Root = new ArrayList<Node>();
	}
	
	/**
	 * Insert an array of sentences in the Trie.
	 * Array of sentence --> Sentences class
	 * @param trainingData The list of sentences encapsulated in Sentences type.
	 */
	public void InsertIntoTrie(Sentences trainingData) {
		/* 
		 * For each sentence in the list,
		 * Insert into the trie if it doesn't exist already.
		 */
		for (Sentence sentence : trainingData.getSentence()) {
			InsertIntoTrie(sentence);
		}
	}
	/**
	 * Insert a sentence into the Trie.
	 * @param sentence The sentence to be added.
	 */
	public void InsertIntoTrie(Sentence sentence) {
		/*Search if the root contains*/
		Iterator<Node> rootIterator = Root.iterator();
		Node parent = null;
		
		/*Search if the the branch exists in root*/
		while (rootIterator.hasNext()) {
			Node current = rootIterator.next();
			if (current.getTag().compareTo(sentence.getTokens().get(0).getPost()) == 0) {
				parent = current;
				break;
			}
		}
		/*if parent equals null -> nothing found in the root. Start a new branch*/
		if (parent == null) {
			Word first = sentence.getTokens().get(0);
			Node node = new Node(first.getPost(), first.getName());
			Root.add(node);
			InsertRemaining(sentence, 1, node);
		}
		else {
			/*Branch already exists, update*/
			Iterator<Word> wordIterator = sentence.getTokens().iterator();
			
			@SuppressWarnings("unused")
			Word first = wordIterator.next();
			while (wordIterator.hasNext()) {
				Word currentWord = wordIterator.next();
				Node found = parent.FindChild(currentWord.getPost());
				if (found != null)
					parent = found;
				else {
					InsertRemaining(sentence, sentence.getTokens().indexOf(currentWord), parent);
					break;
				}
			}
		}
	}
	
	/**
	 * Insert all the remaining tags of the sentence in trie.
	 * 
	 * @param sentence Sentence from which all the tags are to be put in the trie
	 * @param position Position from where to start
	 * @param parent Continue here in the Trie
	 */
	private void InsertRemaining(Sentence sentence, int position, Node parent) {
		for (int i = position; i < sentence.getTokens().size(); i++) {
			Word word = sentence.getTokens().get(i);
			Node child = new Node(word.getPost(), word.getName());
			parent.AddChild(child);
			parent = child;
		}
	}

	/**
	 * Implementation Pending
	 * Look for a sequence in the Trie. Match the closest possible option and return.
	 * @param sentence The sentence the look for.
	 * @return
	 */
	public LeafNode Lookup(Sentence sentence) {
		/*
		 * Gameplan:
		 * Search the trie for exact match
		 * If ExactMatch not found,
		 * Search by removing stop words - Use AdvancedNodeSkip Algorithm
		 * If not found, calculate EditDistance and find the nearest branch
		 * Give the DataModel of the nearst match.
		 */
		//FIXME Implement This
		return null;
	}
	
	/*
	 * Trie print functions
	 */
	
	/**
	 * Display the trie graphically on the std out.
	 */
	public void PrintTrie() {
		for (Node node : Root) {
			System.out.println();
			TraverseAndPrint(node, 0);
		}
	}
	private void TraverseAndPrint(Node node, int level) {
		//OffsetToLevel(level);
		while (node.getChildren().get(0) != null) {
			level++;
			PrintNode(node);
			if (node.getChildren().size() > 1) {
				for (int i = 1; i < node.getChildren().size(); i++) {
					TraverseAndPrint(node.getChildren().get(i), level);
					OffsetToLevel(level);
				}
			}
			node = node.getChildren().get(0);
			if (node.getChildren().isEmpty() == true) {
				/*No more children to mine. Print and break*/
				PrintNode(node);
				break; 
			}
		}
	}
	private void PrintNode(Node node) {
		//System.out.printf("%-5s(%1.2f) ", node.getTag(), node.getIsStopWordProbability()); //print with probability
		System.out.printf("%-5s", node.getTag());
	}
	private void OffsetToLevel(int level) {
		System.out.println();
		for (int i = 0; i < level; i++) {
			//System.out.printf("%-12s", "|"); //offset with probability
			System.out.printf("%-5s", "|");
			
		}
	}
}
