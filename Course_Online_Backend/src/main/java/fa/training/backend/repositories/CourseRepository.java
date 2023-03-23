package fa.training.backend.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import fa.training.backend.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fa.training.backend.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    public Optional<Course> findById(int id);

    @Query("select c from Course c where c.createDate < ?1 and c.id = ?2")
    Optional<Course> findWithStartDateTimeBeforePresent(Date date, int id);

    @Query("SELECT c FROM Course c JOIN c.categories ct WHERE ct.id = ?1")
    public List<Course> findCoursesByCategory(int categoryId, Pageable pageable);

    public List<Course> findCourseByCategoriesIn(List<Category> categories);

    public List<Course> findCourseByCategoriesIn(List<Category> categories, Pageable pageable);


    @Query(" from Course ")
    public List<Course> customGetCouse();

    public List<Course> findByCourseNameIgnoreCaseContaining(String courseName, Pageable pageable);

    @Query("select count(c.id) from Course c JOIN c.categories ct WHERE ct.id = ?1")
    public int countAllCourses(int categoryId);
    List<Course> findCoursesByCategoriesId(int id);
    List<Course> findCoursesByTeacherId(int id);
}
