package erTagger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import nlp.objects.Sentence;
import nlp.objects.Sentences;
import nlp.objects.TagDataLoader;
import nlp.processing.StanfordProcessor;
import trie.Trie;
import trie.serial.SerialTrie;
import util.logging.LoggerSetup;

/**
 * This is the tagger class that can actually be used in the program.
 * 
 * @author Suresh Sarda
 *
 */
public class ERTagger
{

	private static final String[] trainingDataFiles = {"data/training/MegaTraining.xml"};
	// private static final String statFile = "out/stat.csv";

	SerialTrie sTrie;

	private Logger logger = Logger.getLogger(this.getClass().getName());
	public ERTagger() throws JAXBException, IOException
	{
		LoggerSetup.setup(logger);
		logger.setLevel(Level.ALL);

		logger.info("Training model");
		trainModel();
	}

	public void trainModel() throws JAXBException, IOException
	{
		/* Load and Train the Trie */
		logger.info("Loading Trie with training sentences...");
		Sentences sentences = loadTrainingSentences();
		Trie trie = new Trie();
		trie.insert(sentences);

		/* Create and insert data in Serial Trie from original Trie */
		logger.info("Serializing trie...");
		sTrie = new SerialTrie(trie);

		logger.info("Loading TagData...");
		TagDataLoader.getInstance().Load();
	}

	public void tagFile(File file) throws IOException
	{
		List<String> paragraphs = Files.readAllLines(file.toPath());
		logger.info("Number of paragraphs read: " + paragraphs.size());

		for (String para : paragraphs)
		{
			tagParagraph(para);
		}
	}

	public void tagParagraph(String paragraph)
	{
		LOGGER.info("Splitting and trying to tag sentence...");

		List<String> sentences = StanfordProcessor.getInstance().ParagraphToSentences(paragraph);
		for (String sentence : sentences)
		{
			sTrie.Lookup(new Sentence(sentence));
		}

	}

	public static final Logger LOGGER = Logger.getLogger("Global");

	public static void main(String[] args) throws JAXBException, IOException
	{

		LoggerSetup.setup(LOGGER);

		LOGGER.setLevel(Level.INFO);

		ERTagger tool = new ERTagger();
		tool.trainModel();
		tool.tagParagraph("A comment can be meant for the reviewer or for the author.");

		/* Generate Statistics */
		// File out = new File(statFile);
		// PrintStream ps = new PrintStream(out);
		// StatisticsCollector.Analyze(sentences, ps);

		// LOGGER.info("Loading test paragraph...");
		// Paragraph p = new Paragraph(new File(testDataFile));
		//
		// LOGGER.info("Acquiring data model...");
		// p.acquireDataModel(trie);
		//
		// LOGGER.fine(p.getParagraphDataModel().toString());
		//
		// LOGGER.info("Saving the output...");
		// p.saveAsXml(outputFile);
	}

	private static Sentences loadTrainingSentences() throws JAXBException
	{

		JAXBContext jc = JAXBContext.newInstance(Sentences.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		/*
		 * The following code overrides the default behavior of JABX which
		 * silently ignores error. Source:
		 * http://stackoverflow.com/questions/2633276
		 * /jaxb-unmarshall-created-an-empty-object
		 */
		unmarshaller.setEventHandler(new ValidationEventHandler()
		{
			@Override
			public boolean handleEvent(ValidationEvent event)
			{
				throw new RuntimeException(event.getMessage(), event.getLinkedException());
			}
		});

		Sentences sentences = new Sentences();

		int totalTrained = 0;

		LOGGER.config("Unmarshalling training sentences...");
		for (int i = 0; i < trainingDataFiles.length; i++)
		{

			File xml = new File(trainingDataFiles[i]);
			Sentences sentSet = (Sentences) unmarshaller.unmarshal(xml);
			sentences.addSentence(sentSet.getSentence());

			LOGGER.config(String.format("Reading %d sentences from file %s...", sentSet
					.getSentence().size(), trainingDataFiles[i]));
			totalTrained += sentSet.getSentence().size();
		}

		LOGGER.config(String.format("Read %d sentences.", totalTrained));
		return sentences;
	}
}
