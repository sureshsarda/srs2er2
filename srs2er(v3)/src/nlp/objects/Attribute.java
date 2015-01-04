package nlp.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import nlp.processing.StanfordProcessor;

public class Attribute implements Comparable<Attribute> {
	private int Id;
	private int WordId;
	private int Length;
	private String Name;
	private String LemmName;

	/* Getters and Setters */
	@XmlAttribute(name = "Id")
	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	@XmlAttribute(name = "WordId")
	public int getWordId() {
		return WordId;
	}

	public void setWordId(int WordId) {
		this.WordId = WordId ;
	}

	@XmlAttribute(name = "Length")
	public int getLength() {
		return Length;
	}

	public void setLength(int Length) {
		this.Length = Length;
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
			this.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		return LemmName;
	}

	@Override
	public String toString() {
		return this.Name;
	}

	@Override
	public int compareTo(Attribute attribute) {

		if (this.LemmName == null) {
			this.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		if (attribute.LemmName == null) {
			attribute.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		
		return this.LemmName.compareTo(attribute.LemmName);
	}

	public boolean equals(Attribute attribute) {

		if (this.LemmName == null) {
			this.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}
		if (attribute.LemmName == null) {
			attribute.LemmName = StanfordProcessor.getInstance().LemmatisedString(this.Name);
		}

		if (this.LemmName.compareTo(attribute.LemmName) == 0) {
			return true;
		} else {
			return false;
		}

	}
}