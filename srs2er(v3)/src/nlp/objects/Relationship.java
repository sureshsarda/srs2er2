package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import nlp.processing.StanfordProcessor;

public class Relationship extends Type {

	List<RelationEntity> Connects = new ArrayList<RelationEntity>();
	
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
		sb.append(super.toString());
		sb.append(" [");
		for (RelationEntity entity: Connects) {
			sb.append(entity.toString() + ",");
		}
		sb.append("]");
		
		return sb.toString();
	}
	public String toAbstractString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(super.toAbstractString());
		sb.append(" [");
		for (RelationEntity entity: Connects) {
			sb.append(entity.toAbstractString() + ",");
		}
		sb.append("]");
		
		return sb.toString();
	}
	public boolean equals(Relationship relation) {
		if (this.getLemmName().equals(relation.getLemmName())) {
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