package nlp.objects;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Relationship {
	private int Id;
	private int WordId;
	private int Length;
	private String Name;
	private List<RelationEntity> Connects = new ArrayList<RelationEntity>();
	
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
	
	@XmlElementWrapper(name="Connects")
	@XmlElement(name = "Entity")
	public List<RelationEntity> getConnects() {
		return Connects;
	}
	public void setConnects(List<RelationEntity> connects) {
		Connects = connects;
	}

}
