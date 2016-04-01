package messagesservice.model;

public class Tmp {

	int id;
	String subject;
	
	public Tmp() {}
	
	public Tmp(int id, String subject) {
		super();
		this.id = id;
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
