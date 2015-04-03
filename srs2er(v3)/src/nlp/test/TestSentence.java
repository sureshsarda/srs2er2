package nlp.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.Word;
import nlp.processing.StanfordProcessor;
import nlp.processing.TextProcessor;
import edu.stanford.nlp.util.Triple;

public class TestSentence extends Sentence
{
	private List<LookupResultObject> results = null;
	public static final int LIMIT = 4;

	public TestSentence(String sentValue)
	{
		this.Value = sentValue;
		List<Triple<String, String, String>> tokens = StanfordProcessor.getInstance().Annotate(
				sentValue);

		Integer wordIndex = 0;
		List<Word> words = new ArrayList<Word>(tokens.size());
		for (Triple<String, String, String> triple : tokens)
		{
			Word word = new Word();
			word.setId(wordIndex++);
			word.setName(triple.first());
			word.setLemmatizedName(triple.second());
			word.setPost(triple.third());
			words.add(word);

		}
		this.Tokens = words;
		TextProcessor.removePunc(this);
	}

	public void addLookupResult(LookupResultObject obj)
	{
		System.out.println(obj);
		if (getResults() == null)
		{
			setResults(new LinkedList<LookupResultObject>());
		}
		getResults().add(obj);
	}

	public void sortLookupResult()
	{
		Collections.sort(getResults());
	}
	public List<LookupResultObject> getLookupResults()
	{
		return getResults();
	}

	public void discardExtraResults(int limit)
	{
		if (getResults().size() > limit)
			setResults(getResults().subList(0, limit));
	}
	
	public void updateResultIds()
	{
		int id = 0;
		for (LookupResultObject lookupResultObject : results)
		{
			lookupResultObject.id = id++;
		}
	}
	
	public List<LookupResultObject> getResults()
	{
		return results;
	}

	public void setResults(List<LookupResultObject> results)
	{
		this.results = results;
	}
	public int getMinCost()
	{
		return Collections.min(getResults()).getCost();
	}

}
