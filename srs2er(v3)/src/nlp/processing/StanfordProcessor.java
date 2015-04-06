package nlp.processing;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.logging.LoggerSetup;
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

/**
 * The following class is an adapter for Stanford Core NLP Tagger.
 * 
 * @author SureshSarda
 *
 */
public class StanfordProcessor implements Runnable
{
	private static StanfordProcessor instance = null;
	private StanfordCoreNLP pipeline;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private StanfordProcessor()
	{
		// First thing first, setup the logger
		LoggerSetup.setup(logger);
		logger.setLevel(Level.INFO);

		//Init the tagger. This part takes most of the time
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");

		this.pipeline = new StanfordCoreNLP(props);
	}

	/**
	 * Singleton Implementation Returns the instance of StanfordProcessor.
	 * 
	 * @return StanfordProcessor (this class)
	 */
	public static StanfordProcessor getInstance()
	{
		if (instance == null)
		{
			instance = new StanfordProcessor();
		}
		return instance;
	}

	public String lemmatiseString(String string)
	{
		logger.finest("lemmatiseString() - Lemmatising: [" + string + "]");
		if (string == null || string.equals("") == true) {
			logger.warning("Empty string passed for Lemmatisation.");
			return string;
		}
		Annotation annotation = new Annotation(string);
		this.pipeline.annotate(annotation);

		List<CoreLabel> labels = annotation.get(TokensAnnotation.class);
		String lemmString = "";
		for (CoreLabel coreLabel : labels)
		{
			lemmString += coreLabel.get(LemmaAnnotation.class) + "_";
		}
		if (lemmString.endsWith("_") == true)
			lemmString = lemmString.substring(0, lemmString.length() - 1);

		logger.finest(String.format("lemmatiseString() - String: %-15s LemmString: %-15s", string,
				lemmString));
		return lemmString;
	}

	public List<Triple<String, String, String>> Annotate(String text)
	{
		Annotation annotation = new Annotation(text);
		this.pipeline.annotate(annotation);

		List<Triple<String, String, String>> tokens = new LinkedList<Triple<String, String, String>>();

		List<CoreLabel> labels = annotation.get(TokensAnnotation.class);
		for (CoreLabel coreLabel : labels)
		{
			Triple<String, String, String> token = new Triple<String, String, String>(
					coreLabel.get(TextAnnotation.class), coreLabel.get(LemmaAnnotation.class),
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
	public List<String> paragraphToSentences(String paragraphText)
	{
		Annotation paragraph = new Annotation(paragraphText);
		List<String> sentences = new LinkedList<String>();

		this.pipeline.annotate(paragraph);
		List<CoreMap> sentence = paragraph.get(SentencesAnnotation.class);

		for (CoreMap coreMap : sentence)
		{
			sentences.add(coreMap.toString());
		}
		return sentences;
	}

	public String compareLemmatisedString(String string1, String string2)
	{

		logger.finest(String.format("Comparing [%s] and [%s] with their lemmatized names.",
				string1, string2));

		String lemmaString1 = lemmatiseString(string1);
		String lemmaString2 = lemmatiseString(string2);

		if (lemmaString1.compareTo(lemmaString2) == 0)
		{
			logger.finest(String.format("Lemmatized Names are same. [%s]", lemmaString1));
			return lemmaString1;
		}
		else
		{
			logger.finest(String.format("Lemmatized Names are not same. [%s] and [%s]",
					lemmaString1, lemmaString2));
			return null;
		}
	}

	@Override
	public void run()
	{
		StanfordProcessor.getInstance();
		
	}

}
