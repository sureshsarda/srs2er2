package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import nlp.processing.StanfordProcessor;
import edu.stanford.nlp.util.Triple;

public class Sentence {

	private int Id;
	private String Value;
	private List<Word> Tokens = new ArrayList<Word>();
	private Model DataModel = new Model();
	
	public Sentence() {
		/*Default constructor required for marshaling*/
	}

	/**
	 * Create a sentence object with the value specified. Tags part of speech of the sentence.
	 * @param value
	 */
	public Sentence(String value) {
		this.Value = value;
		List<Triple<String, String, String>> tokens = StanfordProcessor.getInstance().Annotate(value);
		
		Integer wordIndex = 0;
		List<Word> words = new ArrayList<Word>(tokens.size());
		for (Triple<String, String, String> triple : tokens) {
			Word word = new Word();
			word.setId(wordIndex++);
			word.setName(triple.first());
			word.setLemmatizedName(triple.second());
			word.setPost(triple.third());
			words.add(word);
			
		}
		this.Tokens = words;
	}
	
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
	
	public List<String> getPostForAttribute(Attribute attribute) {
		int length = attribute.getLength();
		List<String> tags = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			tags.add(Tokens.get(attribute.getWordId() + i).getPost());
		}
		return tags;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.Value);
		sb.append(System.lineSeparator());
		sb.append(String.format(Word.PrintFormat, 00 , "POST", "Name", "Lemmatized"));
		sb.append(System.lineSeparator());
		for (Word word : Tokens) {
			sb.append(word.toString());
			sb.append(System.lineSeparator());
		}
		sb.append(this.DataModel.toString());
		return sb.toString();
	}
}