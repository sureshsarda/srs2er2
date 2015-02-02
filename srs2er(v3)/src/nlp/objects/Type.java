package nlp.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import nlp.processing.StanfordProcessor;

public class Type implements Comparable<Type> {
	String name;
	String lemmName;
	int id;
	int wordIndex;
	int length;
	
	@XmlAttribute(name = "Id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlAttribute(name = "WordId")
	public int getWordIndex() {
		return wordIndex;
	}
	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex - 1;
	}
	
	@XmlAttribute(name = "Length")
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.lemmName = StanfordProcessor.getInstance().lemmatiseString(this.name);
	}
	public String getLemmName() {
		return lemmName;
	}
	
	@Override
	public String toString() {
		return String.format("%s WI%d Ld", lemmName, wordIndex, length);
	}
	
	@Override
	public int compareTo(Type type) {
		if (length == type.length) {
			return lemmName.compareTo(type.lemmName);
		}
		else {
			if (this.length < type.length) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
	
}
