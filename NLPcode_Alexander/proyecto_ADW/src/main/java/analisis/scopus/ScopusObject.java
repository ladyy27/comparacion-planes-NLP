package analisis.scopus;

public class ScopusObject implements Comparable<ScopusObject> {

	private String conceptUri;
	private String conceptName;
	private double score;

	public String getConceptUri() {
		return conceptUri;
	}

	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int compareTo(ScopusObject unescoObject) {
		return Double.compare(unescoObject.getScore(), score);
	}

}
