package nlp.processing;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import nlp.objects.Word;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

public class POSTagger {

	private LexicalizedParser lp = LexicalizedParser
			.loadModel("Data\\Tagger\\englishPCFG.ser.gz");

	/**
	 * Tags the sentence with its POSTs.
	 * 
	 * @param sentence
	 *            Sentence string that is to be tagged
	 * @param sentenceIndex
	 *            Index of sentence in Id Field
	 * @return A sentence object
	 */
	public nlp.objects.Sentence tag(String sentence, int sentenceIndex) {
		nlp.objects.Sentence paragraphSentence = new nlp.objects.Sentence();

		List<SimpleEntry<String, String>> processedWords = new ArrayList<SimpleEntry<String, String>>(tag(sentence));
		
		/*Set Id and value of the sentence*/
		paragraphSentence.setId(sentenceIndex);
		paragraphSentence.setValue(sentence);

		List<Word> tokens = new ArrayList<Word>();
		int wordId = 0;
		for (SimpleEntry<String, String> processedWord : processedWords) {
			Word word = new Word();
			word.setId(wordId++);
			word.setName(processedWord.getKey());
			word.setPost(processedWord.getValue());
			tokens.add(word);
		}
		paragraphSentence.setTokens(tokens);
		return paragraphSentence;
	}

	private List<SimpleEntry<String, String>> tag(String sentence) {
		String[] words = sentence.split(" ");
		List<CoreLabel> rawWords = Sentence.toCoreLabelList(words);
		Tree parse = lp.apply(rawWords);
		List<CoreLabel> tagged = parse.taggedLabeledYield();
		
		/*List of processed words*/
		List<SimpleEntry<String, String>> tokens = new ArrayList<SimpleEntry<String, String>>();
		for (CoreLabel label : tagged) {
			String[] lab = label.toString().split("-");
			SimpleEntry<String, String> token = new SimpleEntry<String, String>(lab[0], lab[1]);
			tokens.add(token);
		}
		return tokens;
	}
}
