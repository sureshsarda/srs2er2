package nlp.objects;

public class Attribute extends Type implements Cloneable
{
	@Override
	public String toString()
	{
		return super.toString();
	}

	@Override
	public String toAbstractString()
	{
		return super.toAbstractString();
	}

	public boolean equals(Attribute attribute)
	{
		if (this.lemmName.compareTo(attribute.lemmName) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}