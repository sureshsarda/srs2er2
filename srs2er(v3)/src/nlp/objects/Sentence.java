package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Sentence {

	private int Id;
	private String Value;
	private List<Word> Tokens = new ArrayList<Word>();
	private Model DataModel;
	
	/* Getters and Setters*/
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
	
	@XmlElementWrapper(name="Tokens")
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
	
	public List<String> getPostForEntity(Integer id) {
		Entity entity = DataModel.getEntities().get(id);
		return getPostForEntity(entity);
	}
	public List<String> getPostForEntity(Entity entity) {
		int length = entity.getLength();
		List<String> tags = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			tags.add(Tokens.get(entity.getWordId() + i).getPost());
		}
		return tags;
	}
	public List<String> getPostForRelationship(Integer id) {
		Relationship relation = DataModel.getRelationships().get(id);
		return getPostForRelationship(relation);
	}
	public List<String> getPostForRelationship(Relationship relation) {
		int length = relation.getLength();
		List<String> tags = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			tags.add(Tokens.get(relation.getWordId() + i).getPost());
		}
		return tags;
	}
	
}
