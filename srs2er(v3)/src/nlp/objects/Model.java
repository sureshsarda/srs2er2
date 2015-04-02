package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Model implements Cloneable{

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
	
	@XmlElementWrapper(name="Relationnships")
	@XmlElement(name = "Relationship")
	public List<Relationship> getRelationships() {
		return Relationships;
	}
	public void setRelationships(List<Relationship> relationships) {
		Relationships = relationships;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ENTITIES");
		sb.append(System.lineSeparator());
		for (Entity entity : Entities) {
			sb.append(entity.toAbstractString());
			sb.append(System.lineSeparator());
		}
		sb.append("RELATIONSHIPS");
		sb.append(System.lineSeparator());
		for (Relationship relationship : Relationships) {
			sb.append(relationship.toAbstractString());
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		
		Model copy =  (Model) super.clone();
		
		copy.Entities = new ArrayList<Entity>();
		for (Entity entity : Entities)
		{
			copy.Entities.add((Entity) entity.clone());
		}
		
		copy.Relationships = new ArrayList<Relationship>();
		for (Relationship relationship : Relationships)
		{
			copy.Relationships.add((Relationship) relationship.clone());
		}
		
		return copy;
	}
}
