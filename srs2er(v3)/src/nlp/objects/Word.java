package nlp.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Word {

	private Integer Id;
	private String Post;
	private String Name;

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
	
	@Override
	public String toString() {
		return this.Id.toString() + " " + this.Name + " " + this.Post;
	}
}
