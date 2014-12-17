package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Model {

	private List<Entity> Entities = new ArrayList<Entity>();
	private List<Relationship> Relationships = new ArrayList<Relationship>();
	
	/*Getters and setters*/
	@XmlElementWrapper(name="Entities")
	@XmlElement(name = "Entity")
	public List<Entity> getEntities() {
		return Entities;
	}
	public void setEntities(List<Entity> entities) {
		Entities = entities;
	}
	
	@XmlElementWrapper(name="Relationships")
	@XmlElement(name = "Relationship")
	public List<Relationship> getRelationships() {
		return Relationships;
	}
	public void setRelationships(List<Relationship> relationships) {
		Relationships = relationships;
	}

	
}
