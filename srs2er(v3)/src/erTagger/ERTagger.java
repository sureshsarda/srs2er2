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

	private SerialTrie sTrie;

	public ERTagger()
	{
		// TODO Remove Global Logger when refactoring completes
		LoggerSetup.setup(LOGGER);
		LOGGER.setLevel(Level.INFO);

		LoggerSetup.setup(logger);
		logger.setLevel(Level.ALL);

		logger.info("Training model");
		trainModel();
	}

	public void trainModel()
	{
		logger.info("Loading TagData...");

		TagDataLoader.getInstance().load();

		/* Load and Train the Trie */
		logger.info("Loading Trie with training sentences...");
		Sentences sentences = null;
		sentences = loadTrainingSentences();

		Trie trie = new Trie();
		trie.insert(sentences);

		/* Create and insert data in Serial Trie from original Trie */
		logger.info("Serializing trie...");
		sTrie = new SerialTrie(trie);

	}

	public void tagFile(File file)
	{
		try
		{
			List<String> paragraphs = Files.readAllLines(file.toPath());
			logger.info("Number of paragraphs read: " + paragraphs.size());

			for (String para : paragraphs)
			{
				tagParagraph(para);
			}
		}
		catch (IOException e)
		{
			logger.severe("Failed to read file.");
		}
	}

	public void tagParagraph(String paragraph)
	{
		LOGGER.info("Splitting and trying to tag sentence...");

		List<String> sentences = StanfordProcessor.getInstance().paragraphToSentences(paragraph);
		for (String sentence : sentences)
		{
			sTrie.Lookup(new Sentence(sentence));
		}

	}

	private Sentences loadTrainingSentences()
	{
		Unmarshaller unmarshaller = null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Sentences.class);
			unmarshaller = jc.createUnmarshaller();

			/* Prints the details of the exception thrown by JABX */
			unmarshaller.setEventHandler(new ValidationEventHandler()
			{
				@Override
				public boolean handleEvent(ValidationEvent event)
				{
					throw new RuntimeException(event.getMessage(), event.getLinkedException());
				}
			});

			Sentences sentences = new Sentences();
			logger.config("Unmarshalling training sentences...");
			for (int i = 0; i < trainingDataFiles.length; i++)
			{
				File xml = new File(trainingDataFiles[i]);
				Sentences sentSet = (Sentences) unmarshaller.unmarshal(xml);
				sentences.addAll(sentSet.getSentence());
			}
			return sentences;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			logger.severe("JABX Exception. Program needs to exit.");
			System.exit(1);
		}
		return null; // Useless
	}

	/* Loggers */
	private Logger logger = Logger.getLogger(this.getClass().getName());
	public static final Logger LOGGER = Logger.getLogger("Global");
}