package nlp.processing.statistics;

class StatisticCollectorObject  {

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
}
