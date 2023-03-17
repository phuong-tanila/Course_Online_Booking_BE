package fa.training.backend.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@JsonSerialize
@Entity
@Table(name = "chapter")
public class Chapter {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "chapter_name")
    private String chapterName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    public Course  course;

    @Column(name="chapter_index")
    public int chapterIndex;
}
