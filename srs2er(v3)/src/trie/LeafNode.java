package trie;

import java.util.LinkedList;
import java.util.List;

import nlp.objects.Model;

public class LeafNode {
	
	private Model DataModel;
	public List<String> sentences = new LinkedList<String>();
	
	public LeafNode(Model dataModel) {
		DataModel = dataModel;
	}
	public Model getDataModel() {
		return DataModel;
	}

	public void setDataModel(Model dataModel) {
		DataModel = dataModel;
	}
	
	@Override
	public String toString() {
		return DataModel.toString();
	}
	
}