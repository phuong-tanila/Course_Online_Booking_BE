package fa.training.backend.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Transactional
@JsonSerialize
@Table(name = "users")
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 6529685098267757690L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	public int id;
	
	@Column
	public String password;
	@Column(length = Integer.MAX_VALUE)
	public String fullname;
	@Column
	public String phone;
	@Column(length = Integer.MAX_VALUE)
	public String email;

	@Column(length = Integer.MAX_VALUE)
	public String avatar;
	@Column
	public String role;
	@Column(length = Integer.MAX_VALUE)
	public String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "createBy", fetch = FetchType.LAZY)
	public Set<Course> coursesCreateBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher", fetch = FetchType.LAZY)
	public Set<Course> coursesTeacher;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "lastUpdateUser", fetch = FetchType.LAZY)
	public Set<Course> coursesLastUpdateUser;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	public Set<Order> orders;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	public Set<Feedback> feedbacks;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(this.getRole()));
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", password='" + password + '\'' +
				", fullname='" + fullname + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", avatar='" + avatar + '\'' +
				", role='" + role + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
