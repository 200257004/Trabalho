package pt.ips.tizito.entities;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import pt.ips.tizito.dtos.AccountDTO;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.annotations.Ignore;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int NAME_MIN_SIZE = 2;
	private static final int NAME_MAX_SIZE = 30;

	private static final int EMAIL_MAX_SIZE = 60;

	private static final int PASSWORD_MIN_SIZE = 3;
	private static final int PASSWORD_MAX_SIZE = 20;

	private static final String LT = "<";
	private static final String GT = ">";

	private static final String BLANK_SPACE = " ";
	private static final String COLON = ":";

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Ignore
	@Column(nullable = false, unique = true)
	private String email;

	@Ignore
	private String password;

	@Ignore
	private Calendar register;

//	FIXME Trocar para false
	@Ignore
//	private boolean active = true;
	private boolean active;

	@Ignore
	private boolean admin;

	@Ignore
	@ManyToMany
	@JoinTable(name = "User_Friend", 
		joinColumns = { @JoinColumn(name = "user") }, 
		inverseJoinColumns = { @JoinColumn(name = "friend") }
	)
	private List<User> friends;

	protected User() {
		this.register = Calendar.getInstance();

		this.friends = new ArrayList<User>();
	}

	public User(String name, String email, String password) {
		this();

		this.validateName(name);

		this.validateEmail(email);

		this.validatePassword(password);

		this.normalizeName(name);

		this.email = email;

		this.encryptPassword(password);
	}

	public User(AccountDTO dto) {
		this(dto.getName(), dto.getEmail(), dto.getPassword());
	}

	private void validateName(String name) {
		if (name == null) {
			throw new NullPointerException(Error.NAME_NOT_NULL);
		}

		if (name.length() < NAME_MIN_SIZE || name.length() > NAME_MAX_SIZE) {
			throw new IllegalArgumentException(String.format(Error.NAME_SIZE, NAME_MIN_SIZE, NAME_MAX_SIZE));
		}

		if (name.contains(LT) || name.contains(GT)) {
			throw new IllegalArgumentException(Error.NAME_NOT_PATTERN);
		}
	}

	private void validateEmail(String email) {
		if (email == null) {
			throw new NullPointerException(Error.EMAIL_NOT_NULL);
		}

		if (email.length() > EMAIL_MAX_SIZE) {
			throw new IllegalArgumentException(String.format(Error.EMAIL_SIZE, EMAIL_MAX_SIZE));
		}

		if (!EmailValidator.getInstance().isValid(email)) {
			throw new IllegalArgumentException(Error.EMAIL_PATTERN);
		}
	}

	private void validatePassword(String password) {
		if (password == null) {
			throw new NullPointerException(Error.PASSWORD_NOT_NULL);
		}

		if (password.length() < PASSWORD_MIN_SIZE || password.length() > PASSWORD_MAX_SIZE) {
			throw new IllegalArgumentException(String.format(Error.PASSWORD_SIZE, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
		}

		if (password.contains(BLANK_SPACE) || password.contains(COLON)) {
			throw new IllegalArgumentException(Error.PASSWORD_PATTERN);
		}
	}

	private void normalizeName(String name) {
		this.name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	private void encryptPassword(String password) {
		String salt = BCrypt.gensalt();

		String hash = BCrypt.hashpw(password, salt);

		this.password = hash;
	}

	public boolean hasPassword(String password) {
		return BCrypt.checkpw(password, this.password);
	}

	public void add(User friend) {
		if (friend == null) {
			throw new NullPointerException(Error.FRIEND_NOT_NULL);
		}

		if (friend.isActive() && friend != this) {
			if (!this.contains(friend)) {
				this.friends.add(friend);

				friend.add(this);
			}
		}
	}

	public void remove(User friend) {
		if (this.contains(friend)) {
			this.friends.remove(friend);

			friend.remove(this);
		}
	}

	public boolean contains(User friend) {
		return this.friends.contains(friend);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		User user = (User) obj;

		return new EqualsBuilder().append(this.email, user.getEmail()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.email).toHashCode();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Calendar getRegister() {
		return (Calendar) register.clone();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAdmin() {
		return admin;
	}

	public List<User> getFriends() {
		return Collections.unmodifiableList(friends);
	}

	@Override
	public String toString() {
		return "User [email=" + email + "]";
	}

}
