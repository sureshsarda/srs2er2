package nlp.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Pooja Mantri, Suresh Sarda
 *
 */

@XmlRootElement(name = "Sentences")
public class Sentences implements Collection<Sentence>
{

	@XmlElement(name = "Sentence")
	private ArrayList<Sentence> sentenceList;

	public ArrayList<Sentence> getSentence()
	{
		return sentenceList;
	}
	public void setSentence(ArrayList<Sentence> SentenceList)
	{
		this.sentenceList = SentenceList;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Sentence sentence : sentenceList)
		{
			sb.append(sentence.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	/*
	 * Collection Interface
	 */
	@Override
	public boolean add(Sentence sentence)
	{
		if (this.sentenceList == null)
		{
			this.sentenceList = new ArrayList<Sentence>();
		}
		this.sentenceList.add(sentence);
		return true;
	}
	@Override
	public boolean addAll(Collection<? extends Sentence> SentenceList)
	{
		if (this.sentenceList == null)
		{
			this.sentenceList = new ArrayList<Sentence>(100);
		}
		this.sentenceList.addAll(SentenceList);
		return true;
	}
	@Override
	public void clear()
	{
		this.sentenceList.clear();

	}
	@Override
	public boolean contains(Object arg0)
	{
		return this.sentenceList.contains(arg0);
	}
	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return this.sentenceList.containsAll(arg0);

	}
	@Override
	public boolean isEmpty()
	{
		return this.sentenceList.isEmpty();
	}
	@Override
	public Iterator<Sentence> iterator()
	{
		return this.sentenceList.iterator();
	}
	@Override
	public boolean remove(Object arg0)
	{
		return this.sentenceList.remove(arg0);
	}
	@Override
	public boolean removeAll(Collection<?> arg0)
	{
		return this.sentenceList.retainAll(arg0);
	}
	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		return this.sentenceList.retainAll(arg0);
	}
	@Override
	public int size()
	{
		return this.sentenceList.size();
	}
	@Override
	public Object[] toArray()
	{
		return this.sentenceList.toArray();
	}
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return this.sentenceList.toArray(arg0);
	}

}
