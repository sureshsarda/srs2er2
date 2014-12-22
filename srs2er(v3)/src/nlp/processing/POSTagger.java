package nlp.processing;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

/* Singleton object*/
public class POSTagger {

	private LexicalizedParser lp = null;
	private static POSTagger instance = null;
	
	public static POSTagger getInstance() {
		if (instance == null) {
			instance = new POSTagger();
		}
		return instance;
	}
	private POSTagger() {
		lp = LexicalizedParser
				.loadModel("Data\\Tagger\\englishPCFG.ser.gz");
	}
	
	/**
	 * Tags the part of speech of the sentence.
	 * @param sentence The sentence string to tag
	 * @return List of SimpleEntry in format <word, POST>
	 */
	public List<SimpleEntry<String, String>> tag(String sentence) {
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
