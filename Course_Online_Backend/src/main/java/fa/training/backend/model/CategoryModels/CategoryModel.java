package fa.training.backend.model.CategoryModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryModel {
    @JsonProperty
    public int id;
    public String categoryName;
}
