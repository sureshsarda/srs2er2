package nlp.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Pooja Mantri, Suresh Sarda
 *
 */

@XmlRootElement(name="Sentences")
public class Sentences {

	@XmlElement(name = "Sentence")
	private ArrayList<Sentence> SentenceList;

	public ArrayList<Sentence> getSentence() {
		return SentenceList;
	}
	public void setSentence(ArrayList<Sentence> SentenceList) {
		this.SentenceList = SentenceList;
	}
	
}
