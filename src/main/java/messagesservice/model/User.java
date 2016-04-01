package messagesservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="USERS")
@DiscriminatorValue("U")
public class User extends ShortUser {
	
	@NotBlank
	@Column(name="PASSWORD")
	private String passwd;
		
	@Transient
	private String oldPasswd = "";
	
	@Transient
	private String newPasswd = "";
	
    @Temporal( value = TemporalType.TIMESTAMP ) 
    @org.hibernate.annotations.Generated(value=GenerationTime.INSERT) 
    @Column(name = "CREATED", nullable = false, insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date created;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_USER_PROFILES", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CONTACTS", joinColumns = @JoinColumn(name = "USER_ID"), 
		inverseJoinColumns = @JoinColumn(name = "CONTACT_ID") )
	private Set<ShortUser> contacts;
	
	public int getId() {
		return super.getId();
	}

	public void setId(int id) {
		super.setId(id);
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	
	public Date getCreated() {
		return created;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public Set<ShortUser> getContacts() {
		return contacts;
	}

	public void setContacts(Set<ShortUser> contactList) {
		this.contacts = contactList;
	}

	public enum Type {
		ADMIN("Admin"),
		USER("User");
		
		String type;
		
		private Type(String type){
			this.type = type;
		}
		
		@Override
		public String toString(){
			return type;
		}
	}
	
}
