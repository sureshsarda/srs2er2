package nlp.objects;

public class Attribute extends Type  {

	@Override
	public String toString() {
		return super.toString();
	}

	public boolean equals(Attribute attribute) {
		if (this.lemmName.compareTo(attribute.lemmName) == 0) {
			return true;
		} else {
			return false;
		}

	}
}