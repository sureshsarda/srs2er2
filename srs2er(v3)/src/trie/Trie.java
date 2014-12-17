package trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.Sentences;
import nlp.objects.Word;

public class Trie {
	private List<Node> Root;
	
	public Trie() {
		Root = new ArrayList<Node>();
	}
	
	public void InsertIntoTrie(Sentences trainingData) {
		/* 
		 * For each sentence in the list,
		 * Insert into the trie if it doesn't exist already.
		 */
		for (Sentence sentence : trainingData.getSentence()) {
			InsertIntoTrie(sentence);
		}
	}
	private void InsertIntoTrie(Sentence sentence) {
		/*Search if the root contains*/
		Iterator<Node> rootIterator = Root.iterator();
		Node parent = null;
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
			/*some part of branch already exists*/
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
	private void InsertRemaining(Sentence sentence, int position, Node parent) {
		for (int i = position; i < sentence.getTokens().size(); i++) {
			Word word = sentence.getTokens().get(i);
			Node child = new Node(word.getPost(), word.getName());
			parent.AddChild(child);
			parent = child;
		}
	}

	
	public LeafNode Lookup(Sentence sentence) {
		return null;
	}
	
	/*
	 * Trie print functions
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
