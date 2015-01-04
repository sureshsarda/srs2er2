package nlp.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import nlp.processing.StanfordProcessor;

public class Entity implements Comparable<Entity>{
	private int Id;
	private int WordId;
	private int Length;
	private String Name;
	private List<Attribute> Attributes = new ArrayList<Attribute>();
	private String Superclass;
	private String LemmName;
	
	/* Getters and Setters*/
	@XmlAttribute(name = "Id")
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	@XmlAttribute(name = "WordId")
	public int getWordId() {
		return WordId;
	}
	public void setWordId(int wordId) {
		WordId = wordId;
	}
	
	@XmlAttribute(name = "Length")
	public int getLength() {
		return Length;
	}
	public void setLength(int length) {
		Length = length;
	}
	
	@XmlElement(name = "Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@XmlElementWrapper(name = "Attributes")
	@XmlElement(name = "Attribute")
	public List<Attribute> getAttributes() {
		return Attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		Attributes = attributes;
	}
	
	@XmlElement(name = "Superclass")
	public String getSuperclass() {
		return Superclass;
	}
	public void setSuperclass(String superclass) {
		Superclass = superclass;
	}

	
	public String getLemmName() {
		if (this.LemmName == null) {
			this.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		return LemmName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.Name);
		sb.append(" [");
		for (Attribute attribute : Attributes) {
			sb.append(attribute.toString() + ",");
		}
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public int compareTo(Entity entity) {
		return this.Name.compareTo(entity.Name);
	}
	public boolean equals(Entity entity) {

		if (this.LemmName == null) {
			this.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		if (entity.LemmName == null) {
			entity.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		
		if (this.LemmName.equals(entity.LemmName)) {
			if (this.Attributes.size() == entity.Attributes.size()) {
				//FIXME Not considered the fact that same entities can be in different order.
				for (int i = 0; i < this.Attributes.size(); i++) {
					if (this.Attributes.get(i).equals(entity.Attributes.get(i)) == true)
						continue;
					else
						return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		return false;
	}
}