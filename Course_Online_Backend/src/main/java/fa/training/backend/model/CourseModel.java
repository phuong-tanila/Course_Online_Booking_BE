package fa.training.backend.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import fa.training.backend.entities.Chapter;
import fa.training.backend.model.CategoryModels.CategoryModel;
import lombok.Data;
import org.mapstruct.BeanMapping;

@Data
public class CourseModel {
	@JsonProperty
	public int id;
	public String courseName;
	public String description;
	public String objective;
	public String suitable;
	public int slot;
	public int tuitionFee;
	public String imageUrl;
	public Date createDate;
	public Date startDate;
	public Date endDate;
	public boolean status;
	public Date lastUpdateDate;
	public UserModel createBy;
	public UserModel teacher;
	public UserModel lastUpdateUser;
	public Set<CategoryModel> categories;
	public Set<FeedbackModel> feedbacks;
	public Set<ChapterModel> chapters;
}
