package fa.training.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.backend.model.CategoryModels.CategoryModel;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderCourseModel {
	@JsonProperty
	public int id;
	public String courseName;
	public String description;
	public String objective;
	public String suitable;
	public String imageUrl;
	public Date createDate;
	public Date startDate;
	public Date endDate;
	public boolean status;
	public UserModel teacher;
	public Set<CategoryModel> categories;
}
