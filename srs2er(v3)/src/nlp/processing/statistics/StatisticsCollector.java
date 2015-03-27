package nlp.processing.statistics;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Relationship;
import nlp.objects.Sentence;
import nlp.objects.Sentences;
import nlp.objects.Word;
import nlp.processing.Stopwords;
import srs2er.ERTagger;

public class StatisticsCollector {

	/**
	 * Analyze the Sentences and print the output to print stream.
	 * 
	 * @param outStream
	 *            : The output stream where the results are to be printed.
	 */

	public static void Analyze(Sentences sentences, PrintStream printer) {
		Map<String, StatisticCollectorObject> Words = new HashMap<String, StatisticCollectorObject>();
		Map<String, StatisticCollectorObject> Posts = new HashMap<String, StatisticCollectorObject>();

		StatisticCollectorObject object = null;

		/*
		 * FIXME: Improve code quality Provide more functionality Shift code to
		 * sentences class
		 */
		/* Add/Update all the tokens */
		for (Sentence sentence : sentences.getSentence()) {
			for (Word word : sentence.getTokens()) {

				if (Words.containsKey(word.getLemmatizedName()) == false)
					Words.put(word.getLemmatizedName(), new StatisticCollectorObject());
				object = Words.get(word.getLemmatizedName());
				object.increaseTotalOccurence();
				Words.replace(word.getLemmatizedName(), object);

				if (Posts.containsKey(word.getPost()) == false)
					Posts.put(word.getPost(), new StatisticCollectorObject());
				object = Posts.get(word.getPost());
				object.increaseTotalOccurence();
				Posts.replace(word.getPost(), object);
			}
			/* Entities */
			for (Entity entity : sentence.getDataModel().getEntities()) {
				for (String post : sentence.getPostForEntity(entity)) {
					if (Posts.containsKey(post) == false)
						Posts.put(post, new StatisticCollectorObject());
					object = Posts.get(post);
					object.increaseTaggedOccurence();
					Posts.replace(post, object);
				}
				for (String word : Arrays.asList(entity.getName().split(" "))) {
					if (Words.containsKey(word) == false)
						Words.put(word, new StatisticCollectorObject());
					object = Words.get(word);
					object.increaseTaggedOccurence();
					Posts.replace(word, object);
				}

				for (Attribute attribute : entity.getAttributes()) {
					try {
						for (String post : sentence
								.getPostForAttribute(attribute)) {
							if (Posts.containsKey(post) == false)
								Posts.put(post, new StatisticCollectorObject());
							object = Posts.get(post);
							object.increaseTaggedOccurence();
							Posts.replace(post, object);
						}

						for (String word : Arrays.asList(attribute.getName()
								.split(" "))) {
							if (Words.containsKey(word) == false)
								Words.put(word, new StatisticCollectorObject());
							object = Words.get(word);
							object.increaseTaggedOccurence();
							Posts.replace(word, object);
						}
					} catch (NullPointerException ex) {
						ERTagger.LOGGER.info("Cannot parse attribute [" + sentence.getValue() + "]");
					}
				}
			}

			/* Relationship */
			for (Relationship relation : sentence.getDataModel()
					.getRelationships()) {
				for (String post : sentence.getPostForRelationship(relation)) {
					if (Posts.containsKey(post) == false)
						Posts.put(post, new StatisticCollectorObject());
					object = Posts.get(post);
					object.increaseTaggedOccurence();
					Posts.replace(post, object);
				}
				for (String word : Arrays.asList(relation.getName().split(" "))) {
					if (Words.containsKey(word) == false)
						Words.put(word, new StatisticCollectorObject());
					object = Words.get(word);
					object.increaseTaggedOccurence();
					Posts.replace(word, object);
				}
			}
					
		}

		for (String word : Words.keySet()) {
			StatisticCollectorObject obj = Words.get(word);
			printer.printf("\n%s,%d,%d,%s", "WORD", obj.getErTagAssigned(),
					obj.getTotalOccurences(), word);
			if (Stopwords.getInstance().contains(word) == true
					&& obj.getErTagAssigned() > 0) {
				printer.printf("*");
			}
		}

		for (String post : Posts.keySet()) {
			StatisticCollectorObject obj = Posts.get(post);
			printer.printf("\n%s,%d,%d,%s", "POST", obj.getErTagAssigned(),
					obj.getTotalOccurences(), post);
		}

		printer.printf("\n\n* The word is assigned an ER tag and is in stop words list");
	}
}