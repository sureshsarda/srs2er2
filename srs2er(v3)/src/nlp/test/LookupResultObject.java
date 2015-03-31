package nlp.test;

import nlp.objects.Model;
import trie.serial.Branch;

public class LookupResultObject implements Comparable<LookupResultObject>
{
	Integer cost;
	Model dataModel;
	Branch branch;

	public LookupResultObject(Integer cost, Model model, Branch branch)
	{
		this.cost = cost;
		this.dataModel = model;
		this.branch = branch;

	}
	public LookupResultObject()
	{

	}

	public String toString()
	{
		return cost + " " + branch + "\n" + dataModel;
	}

	@Override
	public int compareTo(LookupResultObject arg0)
	{
		return cost.compareTo(arg0.cost);
	}

}
