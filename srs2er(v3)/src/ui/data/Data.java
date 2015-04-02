package ui.data;

import java.util.List;

import nlp.objects.Entity;
import nlp.objects.Relationship;
import nlp.test.TestParagraph;

public class Data
{
	public static TestParagraph para;
	
	
	public List<Entity> getEntites(int sentenceIndex, int dataModelIndex) {
		return para.getSentences().get(sentenceIndex).getResults().get(dataModelIndex).getDataModel().getEntities();
	}
	
	public List<Relationship> getRelationships(int sentenceIndex, int dataModelIndex) {
		return para.getSentences().get(sentenceIndex).getResults().get(dataModelIndex).getDataModel().getRelationships();
	}
	

	
}
