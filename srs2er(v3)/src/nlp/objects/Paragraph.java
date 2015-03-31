package nlp.objects;

import java.lang.invoke.MethodHandles.Lookup;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import nlp.processing.StanfordProcessor;
import trie.LeafNode;
import trie.Trie;
import util.Tuple;
import erTagger.ERTagger;

public class Paragraph
{

	private Sentences paragraphSentences;
	private Model paragraphDataModel;

	
	/**
	 * Loads the paragraph provided from paragraph into the list of sentences.
	 * POSTags the sentences.
	 * 
	 * @param paragraph
	 */
	public Paragraph(String paragraph)
	{
		this.paragraphSentences = new Sentences();

		List<String> rawSentences = StanfordProcessor.getInstance().paragraphToSentences(paragraph);
		for (String sentence : rawSentences)
		{
			Sentence sent = new Sentence(sentence);
			paragraphSentences.add(sent);
		}
	}

	public void acquireDataModel(Trie trie)
	{
		logger.info("Acquiring Data Model...");
		for (Sentence sentence : this.paragraphSentences.getSentence())
		{
			logger.config(String.format("Acquiring Data Model for: %s", sentence.getValue()));

			/*LeafNode leafInfo = Lookup.lookup(trie, sentence, new Tuple(100, 80));
			if (leafInfo == null)
			{
				ERTagger.LOGGER.severe("Lookup Permanently Failed.");
			}
			else
			{
				ERTagger.LOGGER.info("Data Model Acquired.");
				sentence.setDataModel(leafInfo.getDataModel());
			}*/
		}
		createDataModel();
	}

	private void createDataModel()
	{
		List<Entity> entities = new LinkedList<Entity>();
		List<Relationship> relationships = new LinkedList<Relationship>();

		for (Sentence sentence : this.paragraphSentences.getSentence())
		{
			entities.addAll(sentence.getDataModel().getEntities());
			relationships.addAll(sentence.getDataModel().getRelationships());
		}
		this.paragraphDataModel = new Model();
		this.paragraphDataModel.setEntities(entities);
		this.paragraphDataModel.setRelationships(relationships);

		mergeDuplicateEntities();
		mergeDuplicateRelationships();

		updateIds();
	}

	private void mergeDuplicateRelationships()
	{
		Set<Relationship> relationSet = new HashSet<Relationship>();

		for (Relationship relationship : this.paragraphDataModel.getRelationships())
		{
			boolean result = relationSet.add(relationship);
			if (result == false)
			{
				ERTagger.LOGGER.info("Duplicate Relationship removed.");
			}
		}
		List<Relationship> newRelationList = new ArrayList<Relationship>(relationSet.size());
		newRelationList.addAll(relationSet);
		this.paragraphDataModel.setRelationships(newRelationList);
	}

	private void mergeDuplicateEntities()
	{
		List<Entity> entities = this.paragraphDataModel.getEntities();
		Integer entitiesSize = entities.size();

		for (int i = 0; i < entitiesSize; i++)
		{
			Entity current = entities.get(i);

			for (int j = i + 1; j < entitiesSize; j++)
			{
				if (current.getLemmName().compareTo(entities.get(j).getLemmName()) == 0)
				{
					/* Merge Entities */
					Entity duplicateName = entities.get(j);
					Set<Attribute> attributeSet = new HashSet<Attribute>();

					attributeSet.addAll(current.getAttributes());
					attributeSet.addAll(duplicateName.getAttributes());

					List<Attribute> newAttributeList = new ArrayList<Attribute>();
					newAttributeList.addAll(attributeSet);

					current.setAttributes(newAttributeList);

					entities.remove(j);
					j = j - 1; // since an element from array is removed.
					entitiesSize = entitiesSize - 1;
				}
			}
		}
		this.paragraphDataModel.setEntities(entities);
	}

	// FIXME Incomplete Code Here.
	private boolean isPromotable(Attribute attribute)
	{
		Iterator<Entity> entityItr = this.paragraphDataModel.getEntities().iterator();
		while (entityItr.hasNext())
		{
			Entity entity = entityItr.next();
			if (entity.getName().compareTo(attribute.getName()) == 0)
			{
				return true;
			}
		}
		return false;
	}

	private void updateIds()
	{
		int idIndex = 1; // common id inedex for entities and relationships
		for (Entity entity : this.paragraphDataModel.getEntities())
		{
			entity.setId(idIndex);
			idIndex++;
		}
		for (Relationship relationship : this.paragraphDataModel.getRelationships())
		{
			relationship.setId(idIndex);
			idIndex++;
		}
	}

	@Override
	public String toString()
	{
		return this.paragraphSentences.toString();
	}

	public Model getParagraphDataModel()
	{
		return this.paragraphDataModel;
	}

	Logger logger = Logger.getLogger(this.getClass().getName());
}