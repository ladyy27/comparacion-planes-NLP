package analisis.scopus;

public class QueryResult {
	private String conceptUri;
	private String conceptName;

	
	public QueryResult(){}
	
	public QueryResult(String conceptUri, String conceptName) {
		super();
		this.conceptUri = conceptUri;
		this.conceptName = conceptName;
	}

	public String getConceptUri() {
		return conceptUri;
	}

	public void setConceptUri(String s) {
		this.conceptUri = s;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String p) {
		this.conceptName = p;
	}
	
}
