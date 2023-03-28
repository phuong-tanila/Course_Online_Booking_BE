package fa.training.backend.repositories;

import fa.training.backend.entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    public Page<Feedback> findAllByCourseId(int id, Pageable pageable);

    public Feedback save(Feedback feedback);

    @Query("select count(f.id) from Feedback f where f.rating = :star")
    public int countStarRating(int star);

}
