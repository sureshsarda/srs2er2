package srs2er;

import java.io.File;
import java.io.IOException;
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
import trie.Trie;
import trie.serial.SerialTrie;
import util.logging.LoggerSetup;

/**
 * 
 * @author Suresh Sarda
 *
 */
public class Srs2er
{

	private static final String[] trainingDataFiles = {"data/training/MegaTraining.xml"};
//	private static final String statFile = "out/stat.csv";

	SerialTrie sTrie;

	public Srs2er() throws JAXBException, IOException
	{
		trainModel();
	}

	public void trainModel() throws JAXBException, IOException
	{
		/* Load and Train the Trie */
		Sentences sentences = loadTrainingSentences();
		Trie trie = new Trie();
		trie.insert(sentences);

		/* Create and insert data in Serial Trie from original Trie */
		sTrie = new SerialTrie(trie);
		TagDataLoader.getInstance().Load();

	}

	public void tagFile()
	{

	}

	public void tagParagraph(String paragraph)
	{
		String[] sentences = paragraph.split(".");
		for (String sentence : sentences)
		{
			sTrie.Lookup(new Sentence(sentence));
		}

	}

	public static final Logger LOGGER = Logger.getLogger(Srs2er.class.getName());

	public static void main(String[] args) throws JAXBException, IOException
	{

		LoggerSetup.setup(LOGGER);

		LOGGER.setLevel(Level.INFO);

		Srs2er tool = new Srs2er();
		tool.trainModel();
		tool.tagParagraph("Student takes a course");

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
