package trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nlp.objects.Model;
import nlp.processing.Stopwords;
/**
 * Node - Represent a node in the trie
 * To init the class, pass a Part Of Speech tag, which cannot be modified later.
 *
 * @author Suresh Sarda
 *
 */
public class Node {
	/*Cannot modify. Only Adding a word is possible*/
	private List<String> Words;
	
	/*Cannot modify once new object is created*/
	private String Tag;
	
	/*Only private function can update the value. Anyone can read*/
	private Double IsStopWordProbability;
	
	public Double getIsStopWordProbability() {
		return IsStopWordProbability;
	}

	/*Cannot remove a child. Only adding is possible*/
	private List<Node> Children;
	
	/* */
	private LeafNode LeafInformation;
	
	public LeafNode getLeafInformation() {
		return LeafInformation;
	}
	public void setLeafInformation(LeafNode leafInformation) {
		LeafInformation = leafInformation;
	}
	
	/* TODO something
	 * Constructors since LeafNode only contains dataModel as data.
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
		
		AddWord(word);
	}
	public void AddChild(Node child) {
		Children.add(child);
	}
	public Node FindChild(String post) {
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
	
	public void AddWord(String word) {
		int wordCount = Words.size();
		double old = IsStopWordProbability;
		
		Words.add(word);
		
		/*Update probability*/
		IsStopWordProbability = (old * wordCount + (Stopwords.getInstance().Contains(word)?1.0:0.0)) / Words.size();
	}
	
}
