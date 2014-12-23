package nlp.objects;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Attribute {
	private int Id;
	private int WordId;
	private int Length;
	private String Name;
	
	/* Getters and Setters*/
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
		this.WordId = WordId;
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
	
	@Override
	public String toString() {
		return this.Name;
	}
}