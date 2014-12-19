package trie;

import nlp.objects.Model;

public class LeafNode {
	private Model DataModel;

	public LeafNode(Model dataModel) {
		DataModel = dataModel;
	}
	public Model getDataModel() {
		return DataModel;
	}

	public void setDataModel(Model dataModel) {
		DataModel = dataModel;
	}
	
}