package fa.training.backend.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import fa.training.backend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u order by role asc")
	List<User> findAll();

	public List<User> findAllByFullnameIgnoreCaseContaining(String fullName, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.email = :searchKey OR u.phone = :searchKey")
	User findByEmailOrPhone(String searchKey);

	@Query("select u.id from User u where u.email = ?1 or u.phone = ?2")
	public List<Integer> checkExistUserEmailorPhone(String email, String phone);
	@Query("select u from User u where u.id = ?1 and u.role = ?2")
	User findUserById(int id, String role);

	@Query("select count(DISTINCT (c.teacher)) from Course c join c.categories cc group by cc.id")
	public List<Integer> countTeachersEachCategory();
}
