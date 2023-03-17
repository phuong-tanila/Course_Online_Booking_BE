package fa.training.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.backend.entities.Course;
import lombok.Data;

@Data
public class FeedbackModel {
    @JsonProperty
    public int id;
    public String comment;
    public int rating;

    public UserModel user;

}
