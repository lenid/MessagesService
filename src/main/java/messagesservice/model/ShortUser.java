package messagesservice.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "S")
@NamedNativeQuery(name = "QUERY_FIND_OUT_OF_CONTACT_LIST", query = "SELECT * FROM users WHERE id NOT IN (SELECT CONTACT_ID FROM"
		+ " contacts where USER_ID = :id) AND id != :id AND STATE = 'Active' AND SSO_ID LIKE :token", resultClass = ShortUser.class)
public class ShortUser {

	public static final String QUERY_FIND_OUT_OF_CONTACT_LIST = "QUERY_FIND_OUT_OF_CONTACT_LIST";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Column(name = "SSO_ID", unique = true, nullable = false)
	private String ssoId;

	@NotBlank
	@Column(name = "FIRST_NAME", nullable = true)
	private String firstName;

	@NotBlank
	@Column(name = "LAST_NAME", nullable = true)
	private String lastName;

	@NotBlank
	@Column(name = "EMAIL", nullable = true)
	private String email;

	@NotBlank
	@Column(name="STATE", nullable=false)
	private String state=State.ACTIVE.toString();
	
	public ShortUser() {
	}

	public ShortUser(ShortUser shortUser) {
		id = shortUser.getId();
		ssoId = shortUser.getSsoId();
		firstName = shortUser.getFirstName();
		lastName = shortUser.getLastName();
		email = shortUser.getEmail();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ShortUser))
			return false;
		ShortUser other = (ShortUser) obj;
		if (id != other.id)
			return false;
		if (ssoId == null) {
			if (other.ssoId != null)
				return false;
		} else if (!ssoId.equals(other.ssoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShortUser [id=" + id + ", ssoId=" + ssoId + "]";
	}

}
