package nlp.processing;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

public class StanfordProcessor {

	private static StanfordProcessor instance = null;
	private StanfordCoreNLP pipeline;

	private StanfordProcessor() {
		Properties props;
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");

		this.pipeline = new StanfordCoreNLP(props);
	}

	public List<Triple<String, String, String>> Annotate(String text) {
		Annotation annotation = new Annotation(text);
		this.pipeline.annotate(annotation);
		
		List<Triple<String, String, String>> tokens = new LinkedList<Triple<String, String, String>>();

		List<CoreLabel> labels = annotation.get(TokensAnnotation.class);
		for (CoreLabel coreLabel : labels) {
			Triple<String, String, String> token = new Triple<String, String, String>(
					coreLabel.get(TextAnnotation.class),
					coreLabel.get(LemmaAnnotation.class),
					coreLabel.get(PartOfSpeechAnnotation.class));
			tokens.add(token);
		}
		return tokens;
		
	}
	
	/**
	 * Convert paragraph into sentences.
	 * 
	 * @param paragraphText
	 *            Paragraph text
	 * @return List of sentence (string) in paragraph.
	 */
	public List<String> ParagraphToSentences(String paragraphText) {
		Annotation paragraph = new Annotation(paragraphText);
		List<String> sentences = new LinkedList<String>();

		this.pipeline.annotate(paragraph);
		List<CoreMap> sentence = paragraph.get(SentencesAnnotation.class);

		for (CoreMap coreMap : sentence) {
			sentences.add(coreMap.toString());
		}
		return sentences;
	}

	public static StanfordProcessor getInstance() {
		if (instance == null) {
			instance = new StanfordProcessor();
		}
		return instance;
	}
}
