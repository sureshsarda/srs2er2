package nlp.test;

import java.util.ArrayList;
import java.util.List;

import nlp.processing.StanfordProcessor;
import trie.serial.SerialTrie;

public class TestParagraph
{
	private List<TestSentence> sentences;

	public TestParagraph(String paragraph)
	{
		setSentences(new ArrayList<TestSentence>());
		List<String> rawSentences = StanfordProcessor.getInstance().paragraphToSentences(paragraph);

		int id = 0;
		for (String sentence : rawSentences)
		{
			TestSentence tSent = new TestSentence(sentence);
			tSent.setId(id++);
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
			testSentence.updateResultIds();
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
	
	public List<String> getSentenceList() {
		List<String> sents = new ArrayList<String>(this.sentences.size());
		for (TestSentence sent: this.sentences)
		{
			sents.add(sent.getValue());
		}
		
		return sents;
	}
}
