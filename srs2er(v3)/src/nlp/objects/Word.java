package nlp.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Word {

	private Integer Id;
	private String Post;
	private String Name;
	private String LemmatizedName;

	public String getLemmatizedName() {
		return LemmatizedName;
	}

	public void setLemmatizedName(String lemmatizedName) {
		LemmatizedName = lemmatizedName;
	}

	@XmlAttribute(name = "Id")
	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	@XmlAttribute(name = "POST")
	public String getPost() {
		return Post;
	}

	public void setPost(String Post) {
		this.Post = Post;
	}

	@XmlElement(name = "Name")
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public static final String PrintFormat = "%2d  %-4s %-15s %-15s";
	
	@Override
	public String toString() {
		return String.format(PrintFormat, this.Id, this.Post,this.Name,
				(this.LemmatizedName == null ? "null" : this.LemmatizedName));
	}
}
