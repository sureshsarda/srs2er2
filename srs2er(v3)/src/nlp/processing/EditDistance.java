package nlp.processing;

import nlp.objects.Sentence;
import trie.serial.Branch;

public class EditDistance {
	
	public static int editDistance(Sentence sentence, Branch branch) {
		int l1, l2;

		l1 = branch.size();
		l2 = sentence.getTokens().size();
		int[][] d = new int[l1 + 1][l2 + 1];

		/*Init Insertion and deletion costs*/
		for (int i = 0; i <= l1; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j <= l2; j++) {
			d[0][j] = j;
		}

		/*Init substitution cost*/
		for (int i = 1; i <= l1; i++) {
			for (int j = 1; j <= l2; j++) {
				if (branch.get(i - 1).getTag().compareTo(sentence.getTokens().get(j - 1).getPost()) == 0)
					d[i][j] = d[i - 1][j - 1];
				else {
					/*This is where we will know the change log*/
					d[i][j] = Math.min(Math.min(1 + d[i][j - 1], 1 + d[i - 1][j]),1 + d[i - 1][j - 1]);
				}
			}
		}

		return d[l1][l2];
	}
}
