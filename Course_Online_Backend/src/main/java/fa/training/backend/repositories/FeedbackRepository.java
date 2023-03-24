package fa.training.backend.repositories;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.Feedback;
import fa.training.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    public Feedback save(Feedback feedback);
    Page<Feedback> findAllByCourseId(int id, Pageable pageable);
    List<Feedback> findFeedbackByCourse(Course course);
    List<Feedback> findFeedbackByCourse(Course course, Sort sort);

    List<Feedback> findFeedbackByUser(User user);

}
