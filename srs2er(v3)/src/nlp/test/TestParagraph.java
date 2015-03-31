package nlp.test;

import java.util.ArrayList;
import java.util.List;

import nlp.objects.Sentence;
import nlp.processing.StanfordProcessor;
import trie.serial.SerialTrie;

public class TestParagraph
{
	List<TestSentence> sentences;

	public TestParagraph(String paragraph)
	{
		sentences = new ArrayList<TestSentence>();
		List<String> rawSentences = StanfordProcessor.getInstance().paragraphToSentences(paragraph);

		for (String sentence : rawSentences)
		{
			TestSentence tSent = new TestSentence(sentence);
			sentences.add(tSent);
		}
	}

	public void generateLookupResults(SerialTrie sTrie)
	{
		for (TestSentence testSentence : sentences)
		{
			sTrie.assignLookupResults(testSentence);
			testSentence.sortLookupResult();
			testSentence.discardExtraResults(4);
			System.out.println();
		}

	}
}
