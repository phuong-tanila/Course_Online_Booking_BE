package fa.training.backend.services;

import java.util.List;
import java.util.Optional;

import fa.training.backend.exception.RecordNotFoundException;
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

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User createUser(User u) {
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

    public List<Integer> checkExistUserEmailorPhone(String email, String phone) {
        return userRepository.checkExistUserEmailorPhone(email, phone);
    }

    public User findById(int id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No user exist for given id");
        }
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public User saveUser(User updatedUser) {
        return userRepository.save(updatedUser);
    }
}
