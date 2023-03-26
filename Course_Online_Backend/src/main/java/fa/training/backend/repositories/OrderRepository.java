package fa.training.backend.repositories;

import fa.training.backend.entities.Category;
import fa.training.backend.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN o.user u WHERE u.id = ?1")
    List<Order> getAllOrderByUserId(int userId, Pageable pageable);
    List<Order> findAll();

}
