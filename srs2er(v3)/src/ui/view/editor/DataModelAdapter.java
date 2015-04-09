package ui.view.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.RelationEntity;
import nlp.objects.Relationship;

public class DataModelAdapter
{
	protected List<String> entities;
	protected List<String> relationships;
	protected List<String> attributes;
	protected Map<String, List<String>> connections;

	public void addEntity(String name) {
		entities.add(name);
	}
	public void addRelationship(String name) {
		relationships.add(name);
	}
	public void addAttribute(String name) {
		attributes.add(name);
	}
	
	
	public DataModelAdapter(Model data)
	{
		try {
			entities = data.getEntitiesAsStringList();
			relationships = data.getRelationshipsAsStringList();
			attributes = data.getAttributesAsStringList();
			connections = data.getConnectorsAsStringMap();	
		}
		catch (NullPointerException e) {
			entities = new ArrayList<String>();
			relationships = new ArrayList<String>();
			attributes = new ArrayList<String>();
			connections = new HashMap<String, List<String>>();
			
		}
		
	}

	public void remove(String name)
	{
		entities.remove(name);
		relationships.remove(name);
		attributes.remove(name);

		connections.remove(name);

		for (String key : connections.keySet())
		{
			connections.get(key).remove(name);
		}
	}

	public Model getDataModel()
	{
		Model model = new Model();
		for (String entity : this.entities)
		{
			Entity ent = new Entity();
			ent.setName(entity);

			model.getEntities().add(ent);
		}

		for (String relation : relationships)
		{
			Relationship rel = new Relationship();
			rel.setName(relation);
			model.getRelationships().add(rel);
		}

		for (String key : connections.keySet())
		{
			if (entities.contains(key) == true)
			{
				/* The Key is an entity */
				Entity ent = model.getEntityByName(key);
				for (String attribute : connections.get(key))
				{
					Attribute attrib = new Attribute();
					attrib.setName(attribute);
					ent.getAttributes().add(attrib);
				}
			}
			else /*The Key is relationship*/
			{

				Relationship relation = model.getRelationshipByName(key);
				for (String string : connections.get(key))
				{
					RelationEntity rent = new RelationEntity();
					rent.setName(string);
					relation.getConnects().add(rent);
				}
			}
		}

		return model;
	}

}
