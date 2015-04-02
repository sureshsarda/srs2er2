package nlp.test;

import nlp.objects.Model;
import trie.serial.Branch;

public class LookupResultObject implements Comparable<LookupResultObject>
{
	private Integer cost;
	private Model dataModel;
	Branch branch;

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
	/**
	 * @return the cost
	 */
	public Integer getCost()
	{
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(Integer cost)
	{
		this.cost = cost;
	}

}
