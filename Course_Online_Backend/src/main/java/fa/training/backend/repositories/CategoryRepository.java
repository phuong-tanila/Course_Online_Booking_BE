package fa.training.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fa.training.backend.entities.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryByCategoryName(String categoryName);
//    Optional<Category> findCategoryById(int id);
    List<Category> findAll();

}
