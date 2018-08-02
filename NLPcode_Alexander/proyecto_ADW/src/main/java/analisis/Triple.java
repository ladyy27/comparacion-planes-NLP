package analisis;

public class Triple {
	private int id;
	private String s;
	private String p;
	private String o;
	
	public Triple(){}
	
	public Triple(int id, String s, String p, String o) {
		super();
		this.id = id;
		this.s = s;
		this.p = p;
		this.o = o;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}
	
}
