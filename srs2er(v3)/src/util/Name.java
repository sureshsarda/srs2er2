package util;

import java.util.List;

import nlp.objects.Word;

public class Name {
	private Name() {
		
	}
	public static String buildName(List<Word> tokens, Integer wordId, Integer length) {
		String name = "";
		for (int i = wordId; i < wordId + length; i++) {
			name += tokens.get(i).getName() + " ";
		}
		name = name.trim();
		name = name.toLowerCase();
		return name;
	}
}
