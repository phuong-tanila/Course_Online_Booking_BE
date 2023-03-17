package fa.training.backend.model.CategoryModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.backend.model.CourseModel;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryModelDetail {
    @JsonProperty
    public int id;
    public String categoryName;
    public Set<CourseModel> courses;
}