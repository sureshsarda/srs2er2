package nlp.test;

import java.util.ArrayList;
import java.util.List;

import nlp.objects.Sentence;
import nlp.processing.StanfordProcessor;
import trie.serial.SerialTrie;

public class TestParagraph
{
	private List<TestSentence> sentences;

	public TestParagraph(String paragraph)
	{
		setSentences(new ArrayList<TestSentence>());
		List<String> rawSentences = StanfordProcessor.getInstance().paragraphToSentences(paragraph);

		for (String sentence : rawSentences)
		{
			TestSentence tSent = new TestSentence(sentence);
			getSentences().add(tSent);
		}
	}

	public void generateLookupResults(SerialTrie sTrie)
	{
		for (TestSentence testSentence : getSentences())
		{
			sTrie.assignLookupResults(testSentence);
			testSentence.sortLookupResult();
			testSentence.discardExtraResults(4);
			System.out.println();
		}

	}

	public List<TestSentence> getSentences()
	{
		return sentences;
	}

	public void setSentences(List<TestSentence> sentences)
	{
		this.sentences = sentences;
	}
}
