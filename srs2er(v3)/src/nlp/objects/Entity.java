package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Entity extends Type {

	private List<Attribute> Attributes = new ArrayList<Attribute>();
	private String Superclass;

	
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

	@Override
	public String toAbstractString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toAbstractString());
		sb.append(" [");
		for (Attribute attribute : Attributes) {
			sb.append(attribute.toAbstractString() + ",");
		}
		sb.append("]");
		
		return sb.toString();
	}

	public boolean equals(Entity entity) {
		if (this.lemmName.equals(entity.lemmName)) {
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