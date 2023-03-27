package fa.training.backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fa.training.backend.entities.User;
import fa.training.backend.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	public List<User> findAllUser(){
		return userRepository.findAll();
	}

	public List<User> findAllByFullnameIgnoreCaseContaining(Integer pageNo, Integer pageSize, String fullName){
        Sort sort = Sort.by(Sort.Direction.ASC, "fullName");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return userRepository.findAllByFullnameIgnoreCaseContaining(fullName, pageable);
    }

	public User createUser(User u){
		return userRepository.save(u);
	}

	public User updateUser(User u){
		return userRepository.save(u);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailOrPhone(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}

	public List<Integer> checkExistUserEmailorPhone(String email, String phone){
		return userRepository.checkExistUserEmailorPhone(email, phone);
	}

	public User findUserById(int id, String role){
		return userRepository.findUserById(id, role);
	}

	public List<User> findListTeacher(String role){
		return userRepository.findListTeacher(role);
	}

//	public Optional<User> getUser(int id) {
//		return userRepository.findById(id);
//	}

	public User saveUser(User updatedUser) {
		return userRepository.save(updatedUser);
	}

	public List<Integer> countTeacherEachCategory(){
		return userRepository.countTeachersEachCategory();
	};

	public List<User> findListTeacher(String role){
		return userRepository.findListTeacher(role);
	}
}
