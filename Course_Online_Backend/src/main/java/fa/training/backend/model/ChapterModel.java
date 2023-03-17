package fa.training.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChapterModel {
    @JsonProperty
    public int id;
    public String chapterName;
    public int chapterIndex;
}
