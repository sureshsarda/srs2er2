package util;

public class Tuple<T1, T2>  {
	public T1 first;
	public T2 second;

	public Tuple(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 first() {
		return first;
	}

	public T2 second() {
		return second;
	}

	public void setFirst(T1 obj) {
		first = obj;
	}

	public void setSecond(T2 obj) {
		second = obj;
	}
	
	@Override
	public String toString() {
		return "(" + first.toString() + "," + second.toString() + ")";
	}

}
