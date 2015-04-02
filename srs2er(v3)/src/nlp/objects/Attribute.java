package nlp.objects;

public class Attribute extends Type
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
	
	public Attribute copy() {
		Attribute copy = new Attribute();
		copy = (Attribute) super.copy();
		return copy;
	}
}