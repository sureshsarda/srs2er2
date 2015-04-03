package ui.view.editor;

import java.util.List;
import java.util.Map;

import nlp.objects.Model;

public class DataModelAdapter
{
	protected List<String> entities;
	protected List<String> relationships;
	protected List<String> attributes;
	protected Map<String, List<String>> connections;
	
	public DataModelAdapter(Model data) {
		entities = data.getEntitiesAsStringList();
		relationships = data.getRelationshipsAsStringList();
		attributes = data.getAttributesAsStringList();
		connections = data.getConnectorsAsStringMap();
	}
	
	public void remove(String name) {
		entities.remove(name);
		relationships.remove(name);
		attributes.remove(name);
		
		connections.remove(name);
		
		for (String key : connections.keySet())
		{
			connections.get(key).remove(name);
		}
	}
	
	public Model getDataModel() {
		
		return null;
	}
	
	
}
