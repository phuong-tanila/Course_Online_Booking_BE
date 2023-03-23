package fa.training.backend.controller;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.User;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.mapper.UserMapper;
import fa.training.backend.model.UserModel;
import fa.training.backend.services.CourseService;
import fa.training.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    /*Show list all user*/
    @GetMapping("/list-user")
    public ResponseEntity<List<User>> getListUser() {
        return ResponseEntity.ok(userService.findAllUser());
    }

    /*Show user theo id*/
    @GetMapping("/user-by-id/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") int id) throws RecordNotFoundException {
        User user = userService.findById(id);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/update-user/{id}")
    public User edit(@PathVariable("id") int id, @RequestBody @Valid User User) {

        Optional<User> optionalUpdatedUser = userService.getUser(id);
        User updatedUser = optionalUpdatedUser.get();

        if (updatedUser == null) {
            return null;
        }

        updatedUser.setFullname(User.getFullname());
        updatedUser.setPhone(User.getPhone());
        updatedUser.setEmail(User.getEmail());
        updatedUser.setAvatar(User.getAvatar());
        updatedUser.setDescription(User.getDescription());

        return userService.saveUser(updatedUser);
    }
}
