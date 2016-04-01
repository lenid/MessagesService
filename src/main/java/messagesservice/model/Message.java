package messagesservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="MESSAGES")
public class Message {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Temporal( value = TemporalType.TIMESTAMP ) 
    @org.hibernate.annotations.Generated(value=GenerationTime.INSERT) 
    @Column(name = "CREATED", nullable = false, insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date created;

	@NotBlank
	@Column(name="SUBJECT", nullable=false)
	private String subject;
	
	@NotBlank
	@Column(name = "BODY", nullable = false, length = 16777215, columnDefinition = "MEDIUMTEXT")
	private String body;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FROM_USER_ID", nullable=false)
	private ShortUser from;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="TO_USER_ID", nullable=false)
	private ShortUser to;
	
	@NotBlank
	@Column(name="STATE", nullable=false)
	private String state=State.ACTIVE.toString();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ShortUser getFrom() {
		return from;
	}

	public void setFrom(ShortUser from) {
		this.from = from;
	}

	public ShortUser getTo() {
		return to;
	}

	public void setTo(ShortUser to) {
		this.to = to;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Message [subject=" + subject + "]";
	}
	
}
