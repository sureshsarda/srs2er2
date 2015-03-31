package nlp.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.TagDataLoader.TagType;
import trie.serial.Branch;
import util.Tuple;

public class EditDistance
{

	public enum Move
	{
		Down, Side, Diagonal
	}

	public enum Operation
	{
		Delete, Insert, Replace, NoChange, Terminate, Start
	}

	public static Integer editDistance(Sentence sentence, Branch branch)
	{
		Integer sentSize = sentence.getTokens().size();
		Integer branchSize = branch.size();

		Integer[][] distMatrix = new Integer[sentSize + 1][branchSize + 1];

		/* Init Insertion and deletion costs */
		distMatrix[0][0] = 0;
		for (int i = 1; i <= branchSize; i++)
		{
			distMatrix[0][i] = distMatrix[0][i - 1]
					+ branch.get(i - 1).getTag().getTagType().getInsertionCost();
		}

		for (int i = 1; i <= sentSize; i++)
		{
			distMatrix[i][0] = distMatrix[i - 1][0]
					+ sentence.getTokens().get(i - 1).getTag().getTagType().getDeletionCost();
		}

		for (int i = 1; i <= sentSize; i++)
		{
			TagType original = sentence.getTokens().get(i - 1).getTag().getTagType();
			for (int j = 1; j <= branchSize; j++)
			{

				TagType target = branch.get(j - 1).getTag().getTagType();

				if (target == original)
				{
					distMatrix[i][j] = distMatrix[i - 1][j - 1];

				}
				else
				{
					int insCost = distMatrix[i][j - 1] + target.getInsertionCost();
					int delCost = distMatrix[i - 1][j] + original.getDeletionCost();
					int subsCost = distMatrix[i - 1][j - 1] + original.getSubstitutionCost(target);

					distMatrix[i][j] = Math.min(Math.min(insCost, delCost), subsCost);
				}

			}
		}
		// printMatrix(distMatrix);
		return distMatrix[sentSize][branchSize];
	}

	public static Tuple<Integer, List<Operation>> editDistanceExtended(Sentence sentence,
			Branch branch)
	{
		Integer sentSize = sentence.getTokens().size();
		Integer branchSize = branch.size();

		Integer[][] distMatrix = new Integer[sentSize + 1][branchSize + 1];
		Move[][] moveMatrix = new Move[sentSize + 1][branchSize + 1];
		Operation[][] opMatrix = new Operation[sentSize + 1][branchSize + 1];

		/* Init Insertion and deletion costs */
		distMatrix[0][0] = 0;
		opMatrix[0][0] = Operation.Start;

		for (int i = 1; i <= branchSize; i++)
		{
			distMatrix[0][i] = distMatrix[0][i - 1]
					+ branch.get(i - 1).getTag().getTagType().getInsertionCost();
			opMatrix[0][i] = Operation.Insert;
		}

		for (int i = 1; i <= sentSize; i++)
		{
			distMatrix[i][0] = distMatrix[i - 1][0]
					+ sentence.getTokens().get(i - 1).getTag().getTagType().getDeletionCost();
			opMatrix[i][0] = Operation.Delete;
		}

		for (int i = 1; i <= sentSize; i++)
		{
			TagType original = sentence.getTokens().get(i - 1).getTag().getTagType();
			for (int j = 1; j <= branchSize; j++)
			{

				TagType target = branch.get(j - 1).getTag().getTagType();

				if (target == original)
				{
					distMatrix[i][j] = distMatrix[i - 1][j - 1];
					opMatrix[i][j] = Operation.NoChange;
					moveMatrix[i][j] = Move.Diagonal;

				}
				else
				{
					int insCost = distMatrix[i][j - 1] + target.getInsertionCost();
					int delCost = distMatrix[i - 1][j] + original.getDeletionCost();
					int subsCost = distMatrix[i - 1][j - 1] + original.getSubstitutionCost(target);

					if (subsCost <= insCost && subsCost <= delCost)
					{
						distMatrix[i][j] = subsCost;
						opMatrix[i][j] = Operation.Replace;
						moveMatrix[i][j] = Move.Diagonal;
					}
					else if (insCost <= delCost && insCost < subsCost)
					{
						distMatrix[i][j] = insCost;
						opMatrix[i][j] = Operation.Insert;
						moveMatrix[i][j] = Move.Side;
					}
					else
					{
						distMatrix[i][j] = delCost;
						opMatrix[i][j] = Operation.Delete;
						moveMatrix[i][j] = Move.Down;
					}
					// distMatrix[i][j] = Math.min(Math.min(insCost, delCost),
					// subsCost);
				}

			}
		}
		// opMatrix[sentSize][branchSize] = Operation.Terminate;
		/*
		 * for (int i = 0; i < opMatrix.length; i++) { for (int j = 0; j <
		 * opMatrix[i].length; j++) System.out.printf("%10s", opMatrix[i][j]);
		 * System.out.println(); } for (int i = 0; i < opMatrix.length; i++) {
		 * for (int j = 0; j < opMatrix[i].length; j++)
		 * System.out.printf("%10s", moveMatrix[i][j]); System.out.println(); }
		 */
		// printMatrix(distMatrix, sentence, branch);
		return new Tuple<Integer, List<Operation>>(distMatrix[sentSize][branchSize],
				getOperationSequence(opMatrix));
	}

	private static List<Operation> getOperationSequence(Operation[][] ops)
	{
		List<Operation> opList = new ArrayList<Operation>();
		int i = ops.length - 1;
		int j = ops[0].length - 1;
		while (ops[i][j] != Operation.Start)
		{
			switch (ops[i][j])
			{
				case NoChange :
					i -= 1;
					j -= 1;
					opList.add(Operation.NoChange);
					break;
				case Replace :
					i -= 1;
					j -= 1;
					opList.add(Operation.Replace);
					break;
				case Insert :
					j -= 1;
					opList.add(Operation.Insert);
					break;
				case Delete :
					i -= 1;
					opList.add(Operation.Delete);
					break;
				default :
					break;
			}
		}
		Collections.reverse(opList);
		return opList;
	}

	@SuppressWarnings("unused")
	private static void printMatrix(Integer[][] matrix, Sentence sentence, Branch branch)
	{
		System.out.printf(" %-4s   %-4s  %s", "", "", branch.toString("[%-4s] "));

		System.out.printf("\n %-4s  ", "", "");
		for (int i = 0; i < matrix[0].length; i++)
		{
			System.out.printf("[%-4d] ", matrix[0][i]);
		}

		for (int i = 1; i < matrix.length; i++)
		{
			System.out.printf("\n[%-4s] ", sentence.getTokens().get(i - 1).getPost());
			for (int j = 0; j < matrix[i].length; j++)
			{
				// Srs2er.LOGGER.fine(matrix[i][j].toString());
				System.out.printf("[%-4d] ", matrix[i][j]);
			}

		}
	}

	public static Sentence updateWordIndexes(Sentence sentence, List<Operation> operations)
	{
		int offset = 0;
		Sentence copy = new Sentence(sentence.getValue());
		int tokenIndex = 0;
		for (int i = 0; i < operations.size(); i++)
		{
			Operation op = operations.get(i);
			if (op == Operation.NoChange || op == Operation.Replace)
			{
				copy.getTokens().get(tokenIndex).setId(tokenIndex + offset);
				tokenIndex++;
			}
			else if (op == Operation.Delete)
			{
				offset -= 1;
				copy.getTokens().get(tokenIndex).setId(-1);
				tokenIndex++;
			}
			else if (op == Operation.Insert)
			{
				offset += 1;
			}
			else
			{
				System.err.println("Invalid Opeartion performed on sentence.");
			}

		}
		return copy;
	}
}
