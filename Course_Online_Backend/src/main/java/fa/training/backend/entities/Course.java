package fa.training.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@JsonSerialize
@Entity
@Table(name = "course")
@EqualsAndHashCode
public class Course implements Serializable {
//	@Jackson
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	public int id;
//	@JsonIgnore
	@Column(length = Integer.MAX_VALUE)
	public String courseName;
	@Column(length = Integer.MAX_VALUE)
	public String description;
	@Column(length = Integer.MAX_VALUE)
	public String objective;
	@Column(length = Integer.MAX_VALUE)
	public String suitable;
	@Column
	public int slot;
	@Column
	public int tuitionFee;
	@Column(length = Integer.MAX_VALUE)
	public String imageUrl;
	@Column
	public Date createDate;
	@Column
	public Date startDate;
	@Column
	public Date endDate;
	@Column
	public boolean status;
	@Column
	public Date lastUpdateDate;
	@Column
	public int soldCount;

	@ManyToOne
	@JoinColumn(name = "create_by", referencedColumnName = "id")
	public User createBy;
	@ManyToOne
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	public User teacher;
	@ManyToOne
	@JoinColumn(name = "last_update_user", referencedColumnName = "id")
	public User lastUpdateUser;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
	public Set<OrderDetail> orderDetails;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "CategoryCourse", joinColumns = {@JoinColumn(referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")})
	public Set<Category> categories;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
	public Set<Feedback> feedbacks;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
	public Set<Chapter> chapters;

}
