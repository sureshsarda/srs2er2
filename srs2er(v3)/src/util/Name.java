package util;

import java.util.Iterator;
import java.util.List;

import nlp.objects.Word;

public class Name {
	private Name() {
		
	}
	public static String buildName(List<Word> tokens, Integer wordId, Integer length) {
		String name = "";
		for (int i = wordId; i < wordId + length; i++) {
			try {
				name += tokens.get(i).getName() + " ";
//				name += getName(tokens, i) + " ";
			}
			catch (ArrayIndexOutOfBoundsException aioobe) {
				erTagger.ERTagger.LOGGER.info(String.format("Failed to build word at wordId: %d and Length: %d", wordId, length));
				break;
			}
			
		}
		name = name.trim();
		name = name.toLowerCase();
		return name;
	}
	private static String getName(List<Word> tokens, Integer wordId) {
		Iterator<Word> itr = tokens.iterator();
		while(itr.hasNext()) {
			Word word = itr.next();
			if (word.getId() == wordId) {
				return word.getName();
			}
		}
		return null;
		
	}
}
