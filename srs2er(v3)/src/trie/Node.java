package trie;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import trie.Trie.PrintDetail;
import nlp.objects.Model;
import nlp.processing.Stopwords;

/**
 * Node - Represent a node in the trie To init the class, pass a Part Of Speech
 * tag, which cannot be modified later.
 *
 * @author Suresh Sarda
 *
 */
public class Node {
	/* Cannot modify. Only Adding a word is possible */
	private List<String> Words;

	/* Cannot modify once new object is created */
	private String Tag;

	/* Only private function can update the value. Anyone can read */
	private Double IsStopWordProbability;

	public Double getIsStopWordProbability() {
		return IsStopWordProbability;
	}

	/* Cannot remove a child. Only adding is possible */
	private List<Node> Children;

	/* */
	private LeafNode LeafInformation;

	public LeafNode getLeafInformation() {
		return LeafInformation;
	}

	public void setLeafInformation(LeafNode leafInformation) {
		LeafInformation = leafInformation;
	}

	/*
	 * TODO something Constructors since LeafNode only contains dataModel as
	 * data.
	 */
	public void setLeafInformation(Model dataModel) {
		LeafInformation = new LeafNode(dataModel);
	}

	public Model getLeafInformation(Model dataModel) {
		return LeafInformation.getDataModel();
	}

	public Node(String tag, String word) {
		Words = new ArrayList<String>();
		Children = new ArrayList<Node>();
		this.Tag = new String(tag);
		IsStopWordProbability = new Double(0);

		addWord(word);
	}

	public void addChild(Node child) {
		Children.add(child);
	}

	public Node findChild(String post) {
		Iterator<Node> itr = Children.iterator();
		while (itr.hasNext()) {
			Node current = itr.next();
			if (current.getTag().compareTo(post) == 0) {
				return current;
			}
		}
		return null;
	}

	public List<Node> getChildren() {
		return Children;
	}

	public String getTag() {
		return Tag;
	}

	public void addWord(String word) {
		int wordCount = Words.size();
		double old = IsStopWordProbability;

		Words.add(word);

		/* Update probability */
		IsStopWordProbability = (old * wordCount + (Stopwords.getInstance()
				.contains(word) ? 1.0 : 0.0)) / Words.size();
	}

	/**
	 * Print node to PrintStream using PrintDetails
	 * 
	 * @param printer
	 *            PrintStream to print
	 * @param printDetail
	 *            level of details to print
	 */
	public void print(PrintStream printer, PrintDetail printDetail, int level) {
		switch (printDetail) {
		case TAGS_ONLY:
			printer.printf("%-5s", this.Tag);
			break;
		case TAGS_AND_PROBABILITY:
			printer.printf("%-5s(%1.2f) ", this.Tag,
					this.IsStopWordProbability); // print with probability
			break;
		case ALL_DETAILS:
			int totalWords = this.Words.size();
			double stopWordsCount = this.IsStopWordProbability * totalWords;
			printer.printf("%-5s(%1.2f %2.0f|%2d) ", this.Tag, this.IsStopWordProbability, stopWordsCount, totalWords);
			break;
		default:
			System.err.println("Invalid PrintDetail flag.");
			break;
		}

		level = level + 1;
		for (int i = 0; i < Children.size(); i++) {
			if (i > 0)
				offsetToLevel(level, printer, printDetail);
			Children.get(i).print(printer, printDetail, level);
		}
	}

	private void offsetToLevel(int level, PrintStream printer,
			PrintDetail printDetail) {
		printer.println();
		for (int i = 0; i < level; i++) {
			switch (printDetail) {
			case TAGS_ONLY:
				printer.printf("%-5s", "|");
				break;
			case TAGS_AND_PROBABILITY:
				printer.printf("%-12s", "|");
				break;
			case ALL_DETAILS:
				printer.printf("%-18s", "|");
				break;
			default: /* Invalid PrintDetail Information */
				System.err.println("Invalid PrintDetail Flag.");
				break;
			}
		}
	}

	@Override
	public String toString() {
		return this.Tag;
	}
}
