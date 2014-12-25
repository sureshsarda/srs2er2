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
		WordId = wordId - 1;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.Name);
		sb.append(" [");
		for (RelationEntity entity: Connects) {
			sb.append(entity.toString() + ",");
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public boolean equals(Relationship relation) {
		//FIXME Compare lemmetized names here
		if (this.Name.equals(relation.Name)) {
			if (this.Connects.size() == relation.Connects.size()) {
				//FIXME Not considered the fact that same entities can be in different order.
				for (int i = 0; i < this.Connects.size(); i++) {
					if (this.Connects.get(i).equals(relation.Connects.get(i)) == true)
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