package fa.training.backend.repositories;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    public Page<Feedback> findAllByCourseId(int id, Pageable pageable);

    public Feedback save(Feedback feedback);

}
