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
	List<LookupResultObject> results = null;

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
		if (results == null)
		{
			results = new LinkedList<LookupResultObject>();
		}
		results.add(obj);
	}

	public void sortLookupResult()
	{
		Collections.sort(results);
	}
	public List<LookupResultObject> getLookupResults()
	{
		return results;
	}

	public void discardExtraResults(int limit)
	{
		results = results.subList(0, limit);
	}

}
