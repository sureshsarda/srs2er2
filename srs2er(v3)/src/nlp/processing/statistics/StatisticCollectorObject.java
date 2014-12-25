package nlp.processing.statistics;

class StatisticCollectorObject implements Comparable<StatisticCollectorObject> {

	private Integer ErTagAssigned;
	private Integer TotalOccurences;

	public StatisticCollectorObject() {
		this.ErTagAssigned = 0;
		this.TotalOccurences = 0;
	}

	public void increaseTaggedOccurence() {
		this.ErTagAssigned++;
	}

	public void increaseTotalOccurence() {
		this.TotalOccurences++;
	}
	
	public Integer getErTagAssigned() {
		return ErTagAssigned;
	}

	public Integer getTotalOccurences() {
		return TotalOccurences;
	}

	@Override
	public int compareTo(StatisticCollectorObject o) {
		if (this.ErTagAssigned == o.ErTagAssigned && this.TotalOccurences == o.TotalOccurences) {
			return 0;
		}
		else if (this.ErTagAssigned > o.ErTagAssigned) {
			return -1;
		}
		return 1;
	}
}
