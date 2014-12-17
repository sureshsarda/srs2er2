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
}
