package nlp.test;

import nlp.objects.Model;
import trie.serial.Branch;

public class LookupResultObject implements Comparable<LookupResultObject>
{
	protected Integer id;
	protected Integer cost;
	protected Model dataModel;
	protected Branch branch;

	public LookupResultObject(Integer cost, Model model, Branch branch)
	{
		this.setCost(cost);
		this.setDataModel(model);
		this.branch = branch;

	}
	public LookupResultObject()
	{

	}

	public String toString()
	{
		return getCost() + " " + branch + "\n" + getDataModel();
	}

	@Override
	public int compareTo(LookupResultObject arg0)
	{
		return getCost().compareTo(arg0.getCost());
	}

	public Model getDataModel()
	{
		return dataModel;
	}
	public void setDataModel(Model dataModel)
	{
		this.dataModel = dataModel;
	}

	public Integer getCost()
	{
		return cost;
	}

	public void setCost(Integer cost)
	{
		this.cost = cost;
	}

}
