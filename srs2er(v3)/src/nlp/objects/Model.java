package nlp.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Model implements Cloneable
{

	private List<Entity> Entities = new ArrayList<Entity>();
	private List<Relationship> Relationships = new ArrayList<Relationship>();

	/* Getters and setters */
	@XmlElementWrapper(name = "Entities")
	@XmlElement(name = "Entity")
	public List<Entity> getEntities()
	{
		return Entities;
	}
	public void setEntities(List<Entity> entities)
	{
		Entities = entities;
	}

	@XmlElementWrapper(name = "Relationnships")
	@XmlElement(name = "Relationship")
	public List<Relationship> getRelationships()
	{
		return Relationships;
	}
	public void setRelationships(List<Relationship> relationships)
	{
		Relationships = relationships;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ENTITIES");
		sb.append(System.lineSeparator());
		for (Entity entity : Entities)
		{
			sb.append(entity.toAbstractString());
			sb.append(System.lineSeparator());
		}
		sb.append("RELATIONSHIPS");
		sb.append(System.lineSeparator());
		for (Relationship relationship : Relationships)
		{
			sb.append(relationship.toAbstractString());
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{

		Model copy = (Model) super.clone();

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

	/*
	 * DataModel Adapter
	 */

	public List<String> getEntitiesAsStringList()
	{
		List<String> entities = new ArrayList<String>();
		for (Entity entity : this.Entities)
		{
			entities.add(entity.getLemmName());
		}
		return entities;
	}

	public List<String> getAttributesAsStringList()
	{
		List<String> attributes = new ArrayList<String>();
		for (Entity entity : Entities)
		{
			for (Attribute attribute : entity.getAttributes())
			{
				attributes.add(attribute.getLemmName());
			}
		}
		return attributes;
	}
	public List<String> getRelationshipsAsStringList()
	{
		List<String> relationships = new ArrayList<String>();
		for (Relationship relation : this.Relationships)
		{
			relationships.add(relation.getLemmName());
		}
		return relationships;
	}

	public Map<String, List<String>> getConnectorsAsStringMap()
	{

		Map<String, List<String>> connections = new HashMap<String, List<String>>();
		for (Entity entity : Entities)
		{
			List<String> attributes = new ArrayList<String>();
			for (Attribute attribute : entity.getAttributes())
			{
				attributes.add(attribute.getLemmName());
			}
			connections.put(entity.getLemmName(), attributes);
		}

		for (Relationship relation : Relationships)
		{
			List<String> rents = new ArrayList<String>();
			for (RelationEntity rent : relation.getConnects())
			{
				rents.add(rent.getLemmName());
			}
			connections.put(relation.getLemmName(), rents);
		}

		return connections;
	}

	public Entity getEntityByName(String name)
	{
		for (Entity entity : Entities)
		{
			if (entity.getLemmName().equals(name) == true)
			{
				return entity;
			}
		}
		return null;
	}
	public Entity getEntityById(int id)
	{
		for (Entity entity : Entities)
		{
			if (entity.getId() == id)
			{
				return entity;
			}
		}
		return null;
	}
}
