package nlp.processing;

import nlp.objects.Sentence;
import nlp.objects.TagDataLoader.TagType;
import srs2er.Srs2er;
import trie.serial.Branch;

public class EditDistance {

    private enum Move {
	Down, Side, Diagonal
    }

    private enum Operation {
	Delete, Insert, Replace
    }



    public static Integer editDistance(Sentence sentence, Branch branch) {
	Integer sentSize = sentence.getTokens().size();
	Integer branchSize = branch.size();

	Integer[][] distMatrix = new Integer[sentSize + 1][branchSize + 1];

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
	//printMatrix(distMatrix);
	return distMatrix[sentSize][branchSize];
    }

    public static Integer editDistanceExtended(Sentence sentence, Branch branch) {
	Integer sentSize = sentence.getTokens().size();
	Integer branchSize = branch.size();

	Integer[][] distMatrix = new Integer[sentSize + 1][branchSize + 1];

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
	printMatrix(distMatrix, sentence, branch);
	return distMatrix[sentSize][branchSize];
    }
    
    private static void printMatrix(Integer[][] matrix, Sentence sentence, Branch branch) {
	System.out.printf(" %-4s   %-4s  %s", "","", branch.toString("[%-4s] "));
	
	System.out.printf("\n %-4s   %-4s  ", "", "");
	for (int i = 0; i < matrix.length; i++) {
	    System.out.printf("[%-4d] ", matrix[0][i]);
	}
	
	for (int i = 1; i < matrix.length; i++) {
	    System.out.printf("\n[%-4s] ", sentence.getTokens().get(i - 1).getPost());
	    for (int j = 0; j < matrix[i].length; j++) {
		//Srs2er.LOGGER.fine(matrix[i][j].toString());
		System.out.printf("[%-4d] ", matrix[i][j]);
	    }
	    
	}
    }
}
