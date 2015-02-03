package nlp.processing;

import nlp.objects.Sentence;
import nlp.objects.TagDataLoader.TagType;
import trie.serial.Branch;

public class EditDistance {

    public enum Change {
	INSERT, DELETE, SUBSTITUTE, NO_CHANGE
    }
    
    public static int editDistance(Sentence sentence, Branch branch) {
	int sentSize = sentence.getTokens().size();
	int branchSize = branch.size();

	int[][] distMatrix = new int[sentSize + 1][branchSize + 1];

	/* Init Insertion and deletion costs */
	distMatrix[0][0] = 0;
	for (int i = 1; i <= branchSize; i++) {
	    distMatrix[0][i] = distMatrix[0][i - 1]
		    + branch.get(i - 1).getTag().getTagType()
			    .getInsertionCost();
	}

	for (int i = 1; i <= sentSize; i++) {
	    distMatrix[i][0] = distMatrix[i - 1][0]
		    + sentence.getTokens().get(i - 1).getTag().getTagType()
			    .getDeletionCost();
	}

	for (int i = 1; i <= sentSize; i++) {
	    TagType original = sentence.getTokens().get(i - 1).getTag()
		    .getTagType();
	    for (int j = 1; j <= branchSize; j++) {

		TagType target = branch.get(j - 1).getTag().getTagType();

		if (target == original) {
		    distMatrix[i][j] = distMatrix[i - 1][j - 1];
		} else {
		    int insCost = distMatrix[i][j - 1]
			    + target.getInsertionCost();
		    int delCost = distMatrix[i - 1][j - 1]
			    + original.getDeletionCost();
		    int subsCost = distMatrix[i - 1][j - 1]
			    + original.getSubstitutionCost(target);

		    distMatrix[i][j] = Math.min(Math.min(insCost, delCost),
			    subsCost);
		}

	    }
	}

	return distMatrix[sentSize][branchSize];

	// int l1, l2;
	// l1 = branch.size();
	// l2 = sentence.getTokens().size();
	// int[][] d = new int[l1 + 1][l2 + 1];
	// for (int i = 0; i <= l1; i++) {
	// d[i][0] = i;
	// }
	// for (int j = 0; j <= l2; j++) {
	// d[0][j] = j;
	// }
	//
	// for (int i = 1; i <= l1; i++) {
	// for (int j = 1; j <= l2; j++) {
	// if (branch.get(i - 1).getTag()
	// .compareTo(sentence.getTokens().get(j - 1).getTag()) == 0)
	// d[i][j] = d[i - 1][j - 1];
	// else {
	// d[i][j] = Math.min(Math.min(1 + d[i][j - 1], 1 + d[i - 1][j]),
	// 1 + d[i - 1][j - 1]);
	// }
	// }
	// }
	// return d[l1][l2];
    }
}
