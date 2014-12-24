package util;

public class Triple <T1, T2, T3> {
	private T1 first;
	private T2 second;
	private T3 third;
	
	public Triple(T1 first, T2 second, T3 third) {
	    this.first = first;
	    this.second = second;
	    this.third = third;
	  }

	  public T1 first() {
	    return first;
	  }

	  public T2 second() {
	    return second;
	  }

	  public T3 third() {
	    return third;
	  }

	  public void setFirst(T1 o) {
	    first = o;
	  }

	  public void setSecond(T2 o) {
	    second = o;
	  }

	  public void setThird(T3 o) {
	    third = o;
	  }

	  @Override
	  public String toString() {
	    return "(" + first.toString() + "," + second.toString() + "," + third.toString() + ")";
	  }

}
