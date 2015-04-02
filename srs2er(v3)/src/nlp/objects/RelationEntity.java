package nlp.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import nlp.processing.StanfordProcessor;

public class RelationEntity implements Cloneable {
	private int Id;
	private String Cardinality;
	private String Participation;
	private String Name;
	private String LemmName;

	// FIXME There is a typo in the XML Cardinality is written as Cardanility
	/*
	 * DO NOT USE YOUR SHIT BRAIN AND CORRECT THIS SPELLING Rest assured you
	 * WILL regret
	 */
	@XmlAttribute(name = "Cardanality")
	public String getCardinality() {
		return Cardinality;
	}

	public void setCardinality(String Cardinality) {
		this.Cardinality = Cardinality;
	}

	@XmlAttribute(name = "Participation")
	public String getParticipation() {
		return Participation;
	}

	public void setParticipation(String Participation) {
		this.Participation = Participation;
	}

	@XmlAttribute(name = "EntityId")
	public int getEntityId() {
		return Id;
	}

	public void setEntityId(int id) {
		this.Id = id;
	}

	@XmlElement(name = "Name")
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLemmName() {
		if (this.LemmName == null) {
			this.LemmName = StanfordProcessor.getInstance().lemmatiseString(
					this.Name);
		}
		return this.LemmName;
	}

	@Override
	public String toString() {
		return this.getLemmName();
	}
	
	public String toAbstractString() {
	    return String.valueOf(this.Id);
	}

	public boolean equals(RelationEntity rent) {
		// FIXME Compare Lemmetized names
		if (this.Name.compareTo(rent.Name) == 0) {
			return true;
		} else {
			return false;
		}
	}
	

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		RelationEntity copy = (RelationEntity) super.clone();
		copy.Id = new Integer(Id);
		copy.Cardinality = new String(Cardinality);
		copy.Participation = new  String(Participation);
		copy.Name = new String(Name);
		copy.LemmName = new String(LemmName);
		
		return copy;
	}
}
