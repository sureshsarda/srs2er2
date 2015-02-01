package trie;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.Sentences;
import nlp.objects.Word;
import srs2er.Srs2er;

/**
 * Trie:
 * 
 * @author Suresh Sarda
 *
 */
public class Trie {
	/* Default print behavior to TAGS ONLY */
	private PrintDetail PrintBehavior = PrintDetail.TAGS_ONLY;
	protected List<Node> Root;

	public enum PrintDetail {
		TAGS_ONLY, // prints only POST of the trie
		TAGS_AND_PROBABILITY, // prints POSTs as well as the probability of that
								// node
		ALL_DETAILS
	};

	
	public List<Node> getRoot() {
		return this.Root;
	}
	/**
	 * Default constructor. Initializes all the elements.
	 */
	public Trie() {
		Root = new ArrayList<Node>();
	}

	/*
	 * ------------------------------------------------------------------------
	 * INSERT INTO TRIE ROUTINES
	 * ------------------------------------------------------------------------
	 */

	/**
	 * Insert an array of sentences in the Trie. Array of sentence --> Sentences
	 * class
	 * 
	 * @param trainingData
	 *            The list of sentences encapsulated in Sentences type.
	 */
	public void insert(Sentences trainingData) {
		/*
		 * For each sentence in the list, Insert into the trie if it doesn't
		 * exist already.
		 */
		for (Sentence sentence : trainingData.getSentence()) {
			Srs2er.LOGGER.finer(String.format("Inserting: [%s]",
					sentence.getValue()));
			insertIntoTrie(sentence);
		}
	}

	/**
	 * Insert a sentence into the Trie.
	 * 
	 * @param sentence
	 *            The sentence to be added.
	 */
	private void insertIntoTrie(Sentence sentence) {
		/* Search if the root contains */
		Iterator<Node> rootIterator = Root.iterator();
		Node parent = null;

		/* Search if the the branch exists in root */
		while (rootIterator.hasNext()) {
			Node current = rootIterator.next();
			if (current.getTag().compareTo(
					sentence.getTokens().get(0).getPost()) == 0) {
				parent = current;
				break;
			}
		}
		/*
		 * if parent equals null -> nothing found in the root. Start a new
		 * branch
		 */
		if (parent == null) {
			Word first = sentence.getTokens().get(0);
			Node node = new Node(first.getPost(), first.getName());
			Root.add(node);
			insertRemaining(sentence, 1, node);
		} else {
			/* Branch already exists, update */
			Iterator<Word> wordIterator = sentence.getTokens().iterator();

			Word first = wordIterator.next();
			parent.addWord(first.getLemmatizedName());
			
			Node found = null;
			
			while (wordIterator.hasNext()) {
				Word currentWord = wordIterator.next();
				found = parent.findChild(currentWord.getPost());
				if (found != null) {
					parent = found;
					found.addWord(currentWord.getLemmatizedName());
				}
				else {
					insertRemaining(sentence,
							sentence.getTokens().indexOf(currentWord), parent);
					break;
				}
			}
			if (found != null) {
				/* It has found an entry and sentence has ended completely. */
				found.setLeafInformation(sentence.getDataModel());
			}
		}
	}

	/**
	 * Insert all the remaining tags of the sentence in trie.
	 * 
	 * @param sentence
	 *            Sentence from which all the tags are to be put in the trie
	 * @param position
	 *            Position from where to start
	 * @param parent
	 *            Continue here in the Trie
	 */
	private void insertRemaining(Sentence sentence, int position, Node parent) {
		for (int i = position; i < sentence.getTokens().size(); i++) {
			Word word = sentence.getTokens().get(i);
			Node child = new Node(word.getPost(), word.getName());
			parent.addChild(child);
			parent = child;
		}
		
		/* Insert leaf information of the sentence */
		parent.setLeafInformation(sentence.getDataModel());
	}

	/*
	 * ------------------------------------------------------------------------
	 * INSERTION ROUTINES END
	 * ------------------------------------------------------------------------
	 */

	/*
	 * ------------------------------------------------------------------------
	 * PRINT FUNCTIONS BEGIN HERE
	 * ------------------------------------------------------------------------
	 */

	/**
	 * DO NOT USE THIS FUNCTION.
	 */
	@Override
	public String toString() {
		// FIXME Output is not formatted properly.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		print(ps);
		try {
			return baos.toString("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Display the Trie graphically on the given PrintStream
	 * 
	 * @param printer
	 *            The PrintStream to print the output
	 * 
	 */
	public void print(PrintStream printer) {
		for (Node node : Root) {
			printer.println();
			node.print(printer, this.PrintBehavior, 0);
		}
	}

	/**
	 * Display the Trie graphically on the given PrintStream
	 * 
	 * @param printer
	 *            The PrintStream to print the output.
	 * @param printDetail
	 *            The level of detail to print as specified by the enum
	 *            PrintDetail. This value will be set as default behavior and
	 *            used in all subsequent calls unless changed otherwise.
	 */
	public void print(PrintStream printer, PrintDetail printDetail) {
		PrintBehavior = printDetail;
		print(printer);
	}
}