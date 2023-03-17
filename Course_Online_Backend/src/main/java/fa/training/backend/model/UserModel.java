package fa.training.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.backend.entities.Chapter;
import fa.training.backend.entities.Course;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
public class UserModel {
    @JsonProperty
    public int id;
    public String fullname;
    public String phone;
    public String email;
    public String role;
    public String avatar;
    public String description;


}
