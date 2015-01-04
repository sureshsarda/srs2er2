package nlp.processing;

import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.Word;

public class TextProcessor {
	public  static void removePunc(Sentence sentence) {
		List<Word> tokens = sentence.getTokens();
		int length = tokens.size();

		for (int i = 0; i < length; i++) {
			Word current = tokens.get(i);
			if (current.getPost().compareTo("POS") == 0
					|| current.getPost().compareTo(",") == 0
					|| current.getPost().compareTo(".") == 0) {
				tokens.remove(current);
				length--;
			}
		}
	}
}
