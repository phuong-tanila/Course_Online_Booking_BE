package fa.training.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.backend.entities.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
public class OrderDetailModel {
    @JsonProperty
    public int id;
    public int price;
    public FeedbackModel feedback;
    public OrderCourseModel course;
}
