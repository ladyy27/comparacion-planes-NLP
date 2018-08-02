package analisis;

public class SubjectWithContext {
	private String code;
	private String subject;
	private String manager;
	
	public SubjectWithContext() {
		super();
	}

	public SubjectWithContext(String code, String subject, String manager) {
		super();
		this.code = code;
		this.subject = subject;
		this.manager = manager;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
}
