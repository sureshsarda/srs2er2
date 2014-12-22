package nlp.objects;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import nlp.processing.POSTagger;

public class Sentence {

	private int Id;
	private String Value;
	private List<Word> Tokens = new ArrayList<Word>();
	private Model DataModel;

	/* Getters and Setters */
	@XmlAttribute(name = "Id")
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	@XmlElement(name = "Value")
	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	@XmlElementWrapper(name = "Tokens")
	@XmlElement(name = "Word")
	public List<Word> getTokens() {
		return Tokens;
	}

	public void setTokens(List<Word> tokens) {
		Tokens = tokens;
	}

	@XmlElement(name = "DataModel")
	public Model getDataModel() {
		return DataModel;
	}

	public void setDataModel(Model dataModel) {
		DataModel = dataModel;
	}
	
	public Sentence() {
		/*Default constructor required for marshaling*/
	}
/*---------- POSTAGS THE SENTENCE---------- */
	public Sentence(String value) {
		this.Value = value;
		int wordIndex = 0;
		for (SimpleEntry<String, String> entry : POSTagger.getInstance().tag(value)) {
			Word word = new Word();
			word.setId(wordIndex++);
			word.setName(entry.getKey());
			word.setPost(entry.getValue());
			this.Tokens.add(word);
		}
	}
	
	
/*---------- POST TAGS FOR ENTITY, RELATIONSHIPS ----------*/
	
	/**
	 * Returns Post tags for an entity.
	 * 
	 * @param id
	 *            Entity Id for which POSTags are to be returned
	 * @return List<String> Containing post tag for every word in name of the
	 *         entity.
	 */
	public List<String> getPostForEntity(Integer id) {
		Entity entity = DataModel.getEntities().get(id);
		return getPostForEntity(entity);
	}

	/**
	 * Returns Post tags for an entity.
	 * 
	 * @param entity
	 *            Entity for which POSTags are to be returned
	 * @return List<String> Containing post tag for every word in name of the
	 *         entity.
	 */
	public List<String> getPostForEntity(Entity entity) {
		int length = entity.getLength();
		List<String> tags = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			tags.add(Tokens.get(entity.getWordId() + i).getPost());
		}
		return tags;
	}

	/**
	 * Returns Post tags for an Relationship.
	 * 
	 * @param id
	 *            Relationship Id for which POSTags are to be returned
	 * @return List<String> Containing post tag for every word in name of the
	 *         relation.
	 */
	public List<String> getPostForRelationship(Integer id) {
		Relationship relation = DataModel.getRelationships().get(id);
		return getPostForRelationship(relation);
	}

	/**
	 * Returns Post tags for an Relationship.
	 * 
	 * @param id
	 *            Relationship for which POSTags are to be returned
	 * @return List<String> Containing post tag for every word in name of the
	 *         relation.
	 */
	public List<String> getPostForRelationship(Relationship relation) {
		int length = relation.getLength();
		List<String> tags = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			tags.add(Tokens.get(relation.getWordId() + i).getPost());
		}
		return tags;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.Value);
		sb.append(System.lineSeparator());
		for (Word word : Tokens) {
			sb.append(word.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}
